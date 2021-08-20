// noinspection ES6ConvertVarToLetConst

var ASM = Java.type('net.minecraftforge.coremod.api.ASMAPI');
var Opcodes = Java.type('org.objectweb.asm.Opcodes');

var MethodInsnNode = Java.type('org.objectweb.asm.tree.MethodInsnNode');
var VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');

// noinspection JSUnusedGlobalSymbols
function initializeCoreMod() {
    return {
        'music': {
            'target': {
                'type': 'METHOD',
                'class': 'net.minecraft.client.Minecraft',
                'methodName': 'doLoadLevel', // DO NOT MAP THIS, this is dev instance only ASM, we do not want this in production!!!
                'methodDesc': '(Ljava/lang/String;Lnet/minecraft/core/RegistryAccess$RegistryHolder;Ljava/util/function/Function;Lcom/mojang/datafixers/util/Function4;ZLnet/minecraft/client/Minecraft$ExperimentalDialogType;Z)V'
            },
            'transformer': function (/*org.objectweb.asm.tree.MethodNode*/ methodNode) {
                var /*org.objectweb.asm.tree.InsnList*/ instructions = methodNode.instructions;
                var inj = [];
                for (var index = instructions.size() - 1; index > 0; index--) {
                    var /*org.objectweb.asm.tree.VarInsnNode*/ node = instructions.get(index);
                    if (node instanceof VarInsnNode &&

                        node.getOpcode() === Opcodes.ALOAD &&

                        node.var === 6

                    )
                        inj.push(node);

                }
                inj.forEach(function (i) {
                    instructions.insert(
                        i,
                        ASM.listOf(
                            new MethodInsnNode(
                                Opcodes.INVOKESTATIC,
                                'twilightforest/ASMHooks',
                                'dragons',
                                '(Lnet/minecraft/client/Minecraft$ExperimentalDialogType;)Lnet/minecraft/client/Minecraft$ExperimentalDialogType;',
                                false
                                )
                            )
                        );
                })
                return methodNode;
            }
        }
    }
}
