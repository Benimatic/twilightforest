package twilightforest.client.shader;

import net.minecraft.client.renderer.OpenGlHelper;

import java.util.function.IntSupplier;

public class ShaderUniformInt extends ShaderUniform {

    private final IntSupplier supplier;

    @SuppressWarnings("WeakerAccess")
    public ShaderUniformInt(String name, IntSupplier supplier) {
        super(name);
        this.supplier = supplier;
    }

    @Override
    public final void assignUniform(int shader) {
        OpenGlHelper.glUniform1i(OpenGlHelper.glGetUniformLocation(shader, name), supplier.getAsInt());
    }
}
