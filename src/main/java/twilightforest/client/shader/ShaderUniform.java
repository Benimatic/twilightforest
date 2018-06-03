package twilightforest.client.shader;

/**
 * Created by Drullkus on 6/2/18.
 */
public abstract class ShaderUniform {
    protected final String name;

    public ShaderUniform(String name) {
        this.name = name;
    }

    public abstract void assignUniform(int shader);
}
