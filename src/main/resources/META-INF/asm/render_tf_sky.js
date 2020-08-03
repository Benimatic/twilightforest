function initializeCoreMod() {
    return {
        'render_tf_sky': {
            'target': {
                'type': 'METHOD',
                'class': 'net.minecraft.client.renderer.WorldRenderer',
                'methodName': 'func_228424_a_',
                'methodDesc': '(Lcom/mojang/blaze3d/matrix/MatrixStack;F)V'
            },
            'transformer': function (methodNode) {
                var Opcodes = Java.type('org.objectweb.asm.Opcodes');
                methodNode.instructions.insert(
                    new org.objectweb.asm.tree.MethodInsnNode( // INVOKESTATIC twilightforest.asm.RenderTFSky#render()
                        Opcodes.INVOKESTATIC,
                        'twilightforest/asm/RenderTFSky',
                        'render',
                        '()V',
                        false
                        )
                    );
                return methodNode;
            }
        }
    }
}