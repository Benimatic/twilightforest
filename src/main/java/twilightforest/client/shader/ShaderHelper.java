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

import net.minecraft.client.renderer.OpenGlHelper;

import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;
import twilightforest.TwilightForestMod;
import twilightforest.client.TFClientEvents;

@SuppressWarnings("ALL")
public final class ShaderHelper {
    private static final int VERT = ARBVertexShader.GL_VERTEX_SHADER_ARB;
    private static final int FRAG = ARBFragmentShader.GL_FRAGMENT_SHADER_ARB;
    private static final String PREFIX = "/assets/twilightforest/shaders/";

    public static int enderPortalShader = 0;
    public static int twilightSkyShader = 0;

    public static void initShaders() {
        if(!useShaders())
            return;

        enderPortalShader = createProgram("starfield.vert", "ender.frag");
        twilightSkyShader = createProgram("starfield.vert", "twilight_sky.frag");
    }

    public static void useShader(int shader, ShaderCallback callback) {
        if(!useShaders())
            return;

        ARBShaderObjects.glUseProgramObjectARB(shader);

        if(shader != 0) {
            int time = ARBShaderObjects.glGetUniformLocationARB(shader, "time");
            ARBShaderObjects.glUniform1iARB(time, TFClientEvents.time);

            if(callback != null)
                callback.call(shader);
        }
    }

    public static void useShader(int shader) {
        useShader(shader, null);
    }

    public static void releaseShader() {
        useShader(0);
    }

    public static boolean useShaders() {
        // TEMA: here's where you'd stick in any config option for enabling/disabling the shaders... don't know how it would interact with the shader mod, etc.

        //return ConfigHandler.useShaders && OpenGlHelper.shadersSupported;
        return OpenGlHelper.shadersSupported;
    }

    // Most of the code taken from the LWJGL wiki
    // http://lwjgl.org/wiki/index.php?title=GLSL_Shaders_with_LWJGL

    public static int createProgram(String vert, String frag) {
        int vertId = 0, fragId = 0, program = 0;
        if(vert != null)
            vertId = createShader(PREFIX + vert, VERT);
        if(frag != null)
            fragId = createShader(PREFIX + frag, FRAG);

        program = ARBShaderObjects.glCreateProgramObjectARB();
        if(program == 0)
            return 0;

        if(vert != null)
            ARBShaderObjects.glAttachObjectARB(program, vertId);
        if(frag != null)
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

            Exception innerExc= null;
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
                    if(innerExc == null)
                        innerExc = exc;
                    else exc.printStackTrace();
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