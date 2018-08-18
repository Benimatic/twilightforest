package twilightforest.client.shader;

import net.minecraft.client.renderer.OpenGlHelper;

public class ShaderUniformFloat2 extends ShaderUniform {

    private final FloatSupplier supplier0;
    private final FloatSupplier supplier1;

    @SuppressWarnings("WeakerAccess")
    public ShaderUniformFloat2(String name, FloatSupplier supplier0, FloatSupplier supplier1) {
        super(name);
        this.supplier0 = supplier0;
        this.supplier1 = supplier1;
    }

    @Override
    public final void assignUniform(int shader) {
        ShaderManager.glUniform2f(OpenGlHelper.glGetUniformLocation(shader, name), supplier0.get(), supplier1.get());
    }
}
