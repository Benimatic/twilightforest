function initializeCoreMod() {
    return {
        'portalinfo': {
            'target': {
                'type': 'METHOD',
                'class': 'net.minecraft.entity.Entity',
                'methodName': 'func_241829_a',
                'methodDesc': '(Lnet/minecraft/world/server/ServerWorld;)Lnet/minecraft/block/PortalInfo;'
            },
            'transformer': function (methodNode) {
                var ASM = Java.type('net.minecraftforge.coremod.api.ASMAPI');
                var Opcodes = Java.type('org.objectweb.asm.Opcodes');
                methodNode.instructions.insertBefore(
                    ASM.findFirstInstruction(methodNode, Opcodes.ARETURN),
                    ASM.listOf(
                        new org.objectweb.asm.tree.VarInsnNode(Opcodes.ALOAD, 0), // PUSH this
                        new org.objectweb.asm.tree.VarInsnNode(Opcodes.ALOAD, 1), // PUSH ServerWorld param
                        new org.objectweb.asm.tree.MethodInsnNode( // INVOKE twilightforest.asm.PortalInfoHook#portalInfo(Entity, ServerWorld)
                            Opcodes.INVOKESTATIC,
                            'twilightforest/asm/PortalInfoHook',
                            'portalInfo',
                            '(Lnet/minecraft/entity/Entity;Lnet/minecraft/world/server/ServerWorld;)Lnet/minecraft/block/PortalInfo;',
                            false
                            )
                        )
                    );
                return methodNode;
            }
        }
    }
}