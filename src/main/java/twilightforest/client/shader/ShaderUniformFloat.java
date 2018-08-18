package twilightforest.client.shader;

import net.minecraft.client.renderer.OpenGlHelper;

public class ShaderUniformFloat extends ShaderUniform {

    private final FloatSupplier supplier;

    @SuppressWarnings("WeakerAccess")
    public ShaderUniformFloat(String name, FloatSupplier supplier) {
        super(name);
        this.supplier = supplier;
    }

    @Override
    public final void assignUniform(int shader) {
        ShaderManager.glUniform1f(OpenGlHelper.glGetUniformLocation(shader, name), supplier.get());
    }
}
