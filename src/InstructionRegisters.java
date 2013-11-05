
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author araperth
 */

class InstructionRegisters {

    int loc1, loc2;
    float val1, val2;
    int ctrl;
    float endRes;
    String operVal;

    // declarations of values to the instruction registers
    public InstructionRegisters() {
        initializeInstructionRegisters();
    }
// initialize the values to the declared variables
    private void initializeInstructionRegisters() {
        ctrl = Const.IDLE;
        endRes = 0f;
        loc1 = loc2 = -1;
        val1 = val2 = 0f;
    }
}