// noinspection ES6ConvertVarToLetConst

var ASM = Java.type('net.minecraftforge.coremod.api.ASMAPI');
var Opcodes = Java.type('org.objectweb.asm.Opcodes');

var MethodInsnNode = Java.type('org.objectweb.asm.tree.MethodInsnNode');
var FieldInsnNode = Java.type('org.objectweb.asm.tree.FieldInsnNode');
var VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');

// noinspection JSUnusedGlobalSymbols
function initializeCoreMod() {
    return {
        'seed': {
            'target': {
                'type': 'METHOD',
                'class': 'net.minecraft.client.player.LocalPlayer',
                'methodName': ASM.mapMethod('m_6083_'), // rideTick
                'methodDesc': '()V'
            },
            'transformer': function (/*org.objectweb.asm.tree.MethodNode*/ methodNode) {
                var /*org.objectweb.asm.tree.InsnList*/ instructions = methodNode.instructions;
                instructions.insert(
                    ASM.findFirstInstruction(methodNode, Opcodes.INVOKESPECIAL),
                    ASM.listOf(
                        new VarInsnNode(Opcodes.ALOAD, 0),
                        new FieldInsnNode(
                            Opcodes.GETFIELD,
                            'net/minecraft/client/player/LocalPlayer',
                            ASM.mapField('f_108618_'), // input
                            'Lnet/minecraft/client/player/Input;'
                            ),
                        new VarInsnNode(Opcodes.ALOAD, 0),
                        new FieldInsnNode(
                            Opcodes.GETFIELD,
                            'net/minecraft/client/player/LocalPlayer',
                            ASM.mapField('f_108618_'), // input
                            'Lnet/minecraft/client/player/Input;'
                            ),
                        new FieldInsnNode(
                            Opcodes.GETFIELD,
                            'net/minecraft/client/player/Input',
                            ASM.mapField('f_108573_'), // shiftKeyDown
                            'Z'
                            ),
                        new VarInsnNode(Opcodes.ALOAD, 0),
                        new MethodInsnNode(
                            Opcodes.INVOKEVIRTUAL,
                            'net/minecraft/world/entity/player/Player',
                            ASM.mapMethod('m_36342_'), // wantsToStopRiding
                            '()Z',
                            false
                            ),
                        new VarInsnNode(Opcodes.ALOAD, 0),
                        new MethodInsnNode(
                            Opcodes.INVOKEVIRTUAL,
                            'net/minecraft/world/entity/Entity',
                            ASM.mapMethod('m_20159_'), // isPassenger
                            '()Z',
                            false
                            ),
                        new MethodInsnNode(
                            Opcodes.INVOKESTATIC,
                            'twilightforest/ASMHooks',
                            'mountFix',
                            '(ZZZ)Z',
                            false
                            ),
                        new FieldInsnNode(
                            Opcodes.PUTFIELD,
                            'net/minecraft/client/player/Input',
                            ASM.mapField('f_108573_'), // shiftKeyDown
                            'Z'
                            )
                        )
                    );
                return methodNode;
            }
        }
    }
}

