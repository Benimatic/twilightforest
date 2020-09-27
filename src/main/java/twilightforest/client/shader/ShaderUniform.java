package twilightforest.client.shader;

import net.minecraft.client.renderer.OpenGlHelper;

import java.util.function.IntSupplier;

public abstract class ShaderUniform {

    protected final String name;

    @SuppressWarnings("WeakerAccess")
    protected ShaderUniform(String name) {
        this.name = name;
    }

    public abstract void assignUniform(int shader);

    public static ShaderUniform create(String name, int value) {
        return new ShaderUniform(name) {
            @Override
            public void assignUniform(int shader) {
                OpenGlHelper.glUniform1i(OpenGlHelper.glGetUniformLocation(shader, name), value);
            }
        };
    }

    public static ShaderUniform create(String name, IntSupplier supplier) {
        return new ShaderUniform(name) {
            @Override
            public void assignUniform(int shader) {
                OpenGlHelper.glUniform1i(OpenGlHelper.glGetUniformLocation(shader, name), supplier.getAsInt());
            }
        };
    }

    public static ShaderUniform create(String name, int value0, int value1) {
        return new ShaderUniform(name) {
            @Override
            public void assignUniform(int shader) {
                ShaderManager.glUniform2i(OpenGlHelper.glGetUniformLocation(shader, name), value0, value1);
            }
        };
    }

    public static ShaderUniform create(String name, IntSupplier supplier0, IntSupplier supplier1) {
        return new ShaderUniform(name) {
            @Override
            public void assignUniform(int shader) {
                ShaderManager.glUniform2i(OpenGlHelper.glGetUniformLocation(shader, name), supplier0.getAsInt(), supplier1.getAsInt());
            }
        };
    }

    public static ShaderUniform create(String name, float value) {
        return new ShaderUniform(name) {
            @Override
            public void assignUniform(int shader) {
                ShaderManager.glUniform1f(OpenGlHelper.glGetUniformLocation(shader, name), value);
            }
        };
    }

    public static ShaderUniform create(String name, FloatSupplier supplier) {
        return new ShaderUniform(name) {
            @Override
            public void assignUniform(int shader) {
                ShaderManager.glUniform1f(OpenGlHelper.glGetUniformLocation(shader, name), supplier.get());
            }
        };
    }

    public static ShaderUniform create(String name, float value0, float value1) {
        return new ShaderUniform(name) {
            @Override
            public void assignUniform(int shader) {
                ShaderManager.glUniform2f(OpenGlHelper.glGetUniformLocation(shader, name), value0, value1);
            }
        };
    }

    public static ShaderUniform create(String name, FloatSupplier supplier0, FloatSupplier supplier1) {
        return new ShaderUniform(name) {
            @Override
            public void assignUniform(int shader) {
                ShaderManager.glUniform2f(OpenGlHelper.glGetUniformLocation(shader, name), supplier0.get(), supplier1.get());
            }
        };
    }
}
