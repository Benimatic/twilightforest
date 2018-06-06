/**
 * Original class written by Vazkii for Botania.
 * Copied from TTFTCUTS' ShadowsOfPhysis
 */

// TEMA: this is the main shader stuff, where the programs are loaded and compiled for the card.
// other relevant files are the shader in /assets/physis/shader/, and the tesr in /client/render/tile/
// they have other comments like this in.

package twilightforest.client.shader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.IntConsumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;
import twilightforest.TFConfig;
import twilightforest.TwilightForestMod;
import twilightforest.client.TFClientEvents;

import javax.annotation.Nullable;

public final class ShaderHelper {
    private static final Minecraft MINECRAFT = Minecraft.getMinecraft();

    private static final int VERT = ARBVertexShader.GL_VERTEX_SHADER_ARB;
    private static final int FRAG = ARBFragmentShader.GL_FRAGMENT_SHADER_ARB;
    private static final String PREFIX = "/assets/twilightforest/shaders/";

    @SuppressWarnings("WeakerAccess")
    public static int enderPortalShader, twilightSkyShader, auroraShader, bloomShader;

    @SuppressWarnings("WeakerAccess")
    public static final ShaderUniformInt TIME = new ShaderUniformInt("time", () -> TFClientEvents.time);
    @SuppressWarnings("WeakerAccess")
    public static final ShaderUniformFloat YAW = new ShaderUniformFloat("yaw", () -> (MINECRAFT.player.rotationYaw * 2.0f * TFClientEvents.PI) / 360.0f);
    @SuppressWarnings("WeakerAccess")
    public static final ShaderUniformFloat PITCH = new ShaderUniformFloat("pitch", () -> -(MINECRAFT.player.rotationPitch * 2.0f * TFClientEvents.PI) / 360.0f);
    @SuppressWarnings("WeakerAccess")
    public static final ShaderUniformInt2 HEIGHT = new ShaderUniformInt2("resolution", () -> MINECRAFT.displayWidth, () -> MINECRAFT.displayHeight);

    public static final ShaderUniform[] ENDER_UNIFORMS = { TIME, YAW, PITCH };
    @SuppressWarnings("unused")
    public static final ShaderUniform[] ALL_UNIFORMS = { TIME, YAW, PITCH, HEIGHT };
    @SuppressWarnings("WeakerAccess")
    public static Framebuffer bloomFbo;

    public static void initShaders() {
        IResourceManager iManager;

        if ((iManager = MINECRAFT.getResourceManager()) instanceof SimpleReloadableResourceManager) {
            ((SimpleReloadableResourceManager) iManager).registerReloadListener(manager -> {
                deleteShader(enderPortalShader);
                deleteShader(twilightSkyShader);
                deleteShader(auroraShader);
                deleteShader(bloomShader);

                initShaderList();
            });
        }

        bloomFbo = new Framebuffer(MINECRAFT.displayWidth, MINECRAFT.displayHeight, true);
    }

    private static void deleteShader(int id) {
        if (id != 0) ARBShaderObjects.glDeleteObjectARB(id);
    }

    private static void initShaderList() {
        enderPortalShader = createProgram("standard.vert", "ender.frag");
        twilightSkyShader = createProgram("standard.vert", "twilight_sky.frag");
        auroraShader      = createProgram("standard.vert", "aurora.frag");
        bloomShader       = createProgram("standard.vert", "bloom.frag");
    }

    public static void useShader(int shader, @Nullable IntConsumer callback) {
        if(!useShaders())
            return;

        ARBShaderObjects.glUseProgramObjectARB(shader);

        if(shader != 0 && callback != null) callback.accept(shader);
    }

    @SuppressWarnings("WeakerAccess")
    public static void useShader(int shader) {
        useShader(shader, null);
    }

    public static void releaseShader() {
        useShader(0);
    }

    @SuppressWarnings("WeakerAccess")
    public static boolean useShaders() {
        // TEMA: here's where you'd stick in any config option for enabling/disabling the shaders... don't know how it would interact with the shader mod, etc.

        //return ConfigHandler.useShaders && OpenGlHelper.shadersSupported;
        return TFConfig.performance.shadersSupported && OpenGlHelper.shadersSupported;
    }

    // Most of the code taken from the LWJGL wiki
    // http://lwjgl.org/wiki/index.php?title=GLSL_Shaders_with_LWJGL

    @SuppressWarnings("SameParameterValue")
    private static int createProgram(String vert, String frag) {
        int program = ARBShaderObjects.glCreateProgramObjectARB();

        if(program == 0)
            return 0;

        int vertId = createShader(PREFIX + vert, VERT);
        ARBShaderObjects.glAttachObjectARB(program, vertId);

        int fragId = createShader(PREFIX + frag, FRAG);
        ARBShaderObjects.glAttachObjectARB(program, fragId);

        ARBShaderObjects.glLinkProgramARB(program);
        if(ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_FALSE) {
            TwilightForestMod.LOGGER.error(getLogInfo(program));
            return 0;
        }

        ARBShaderObjects.glValidateProgramARB(program);
        if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL11.GL_FALSE) {
            TwilightForestMod.LOGGER.error(getLogInfo(program));
            return 0;
        }

        return program;
    }

    private static int createShader(String filename, int shaderType){
        int shader = 0;
        try {
            shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);

            if(shader == 0)
                return 0;

            ARBShaderObjects.glShaderSourceARB(shader, readFileAsString(filename));
            ARBShaderObjects.glCompileShaderARB(shader);

            if (ARBShaderObjects.glGetObjectParameteriARB(shader, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE)
                throw new RuntimeException("Error creating shader: " + getLogInfo(shader));

            return shader;
        }
        catch(Exception e) {
            ARBShaderObjects.glDeleteObjectARB(shader);
            e.printStackTrace();
            return -1;
        }
    }

    private static String getLogInfo(int obj) {
        return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
    }

    private static String readFileAsString(String filename) throws Exception {
        StringBuilder source = new StringBuilder();
        InputStream in = ShaderHelper.class.getResourceAsStream(filename);
        Exception exception = null;
        BufferedReader reader;

        if(in == null)
            return "";

        try {
            reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            Exception innerExc = null;
            try {
                String line;
                while((line = reader.readLine()) != null)
                    source.append(line).append('\n');
            } catch(Exception exc) {
                exception = exc;
            } finally {
                try {
                    reader.close();
                } catch(Exception exc) {
                    innerExc = exc;
                }
            }

            if(innerExc != null)
                throw innerExc;
        } catch(Exception exc) {
            exception = exc;
        } finally {
            try {
                in.close();
            } catch(Exception exc) {
                if(exception == null)
                    exception = exc;
                else exc.printStackTrace();
            }

            if(exception != null)
                throw exception;
        }

        return source.toString();
    }
}