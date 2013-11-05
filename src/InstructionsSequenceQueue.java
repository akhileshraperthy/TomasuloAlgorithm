

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author araperth
 */
public class InstructionsSequenceQueue {

    InstructionQueue que[];

    public InstructionsSequenceQueue() {

       initializeISQ();
    }
    
    private void initializeISQ()
    {
         que = new InstructionQueue[Const.ListQueNum];
        for (int i = 0; i < Const.ListQueNum; i++) {
            que[i] = new InstructionQueue();
        }
    }

    public boolean isBusy(int id) {
        return InstructionQueue.isBusy(que[id]);
    }

    public void setAddr(int id, int addr) {
        que[id].addr = addr;
    }

    public int getAddr(int id) {
        return que[id].addr;
    }

    public void setBusy(int id) {
        InstructionQueue.setBusy(que[id]);
    }

    public void setIdle(int id) {
		idleState(id);
	}

	public void idleState(int id) {
        InstructionQueue.setIdle(que[id]);
    }

    public void setStation(int id, int s) {
        que[id].station = s;
    }

    public int getStation(int id) {
        return que[id].station;
    }
}