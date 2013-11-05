

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author araperth
 */
public class InstructionQueue {

    int pos = Const.IDLE;
    int station = -1;
    int addr = -1;
    float endval = 0f;

    // instruction conditions
    public static boolean isBusy(InstructionQueue instructionQueue) {
        return instructionQueue.pos == Const.BUSY? true: false;
    }

    public static void setBusy(InstructionQueue instructionQueue) {
        instructionQueue.pos = Const.BUSY;
    }

    public static void setIdle(InstructionQueue instructionQueue) {
        instructionQueue.pos = Const.IDLE;
    }
}
