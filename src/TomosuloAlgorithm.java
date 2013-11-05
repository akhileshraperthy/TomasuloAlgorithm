
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author araperth
 */

import java.util.LinkedList;

public class TomosuloAlgorithm {
// storing the values in linkedlist
    InstItemList instNext = null;
    LinkedList<InstItemList> executVal;
    LinkedList<Float> stations;
    LinkedList<InstItemList> QueInst;
    LinkedList<InstItemList> resultList;
    LinkedList<InstItemList> nextWbList;
    Store store;
    RegisterFloatvalues reg_Float;
    InstructionsSequenceQueue queList;
    Registerhold resv_station;
    String insName[] = {"ADDD", "SUBD", "MULD", "DIVD", "LD", "SD", "DADDI", "DADDUI", "DSUBD"};// String constants  
    int clk;
    int count;

    public TomosuloAlgorithm() {
        initializeTomosulo();
    }

    private void initializeTomosulo() {
        reg_Float = new RegisterFloatvalues();
        resv_station = new Registerhold();
        store = new Store();
        stations = new LinkedList<Float>();
        setStationFromConst();
        executVal = new LinkedList<InstItemList>();
        resultList = new LinkedList<InstItemList>();
        nextWbList = new LinkedList<InstItemList>();
        QueInst = new LinkedList<InstItemList>();
        queList = new InstructionsSequenceQueue();
        clk = 0;
        count = 0;
    }

    // displays reservation loc, register values, instruction status
    private void setStationFromConst() {
        for (int i = 0; i < Const.RegNum; i++) {
            stations.add(0f);
        }
        for (int i = 0; i < Const.ListQueNum; i++) {
            stations.add(0f);
        }
        for (int i = 0; i < Const.ResvNum; i++) {
            stations.add(0f);
        }
    }

    public void executeInstr() {
		executeInst();
	}

	public void executeInst() {
//  executes all the instructions 
        for (boolean end = false; !end;) {

            end = true;
            int k = 0;
            clk++;
            issueNextInstruction();
	
            executeInstruction();
            readVal();
            if (instNext != null) {
                executVal.add(instNext);
            }
            if (nextWbList.size() != 0) {
                for (InstItemList instruction : nextWbList) {
                    resultList.add(instruction);
                }
                nextWbList.clear();
            }

            while (k < QueInst.size()) {
                if (QueInst.get(k).rewrite == 0) {
                    end = false;
                    break;
                }
                k++;

            }
        }

    }

    public void doNextStep() {
		nextInstr();
	}

	public void nextInstr() {
// single instruction step
        clk++;
        issueNextInstruction();

        executeInstruction();
        readVal();
        if (instNext != null) {
            executVal.add(instNext);
        }
        if (nextWbList.size() != 0) {
            for (InstItemList instruction : nextWbList) {
                resultList.add(instruction);
            }
            nextWbList.clear();
        }
    }

    // getting list of instructions for ready operands
    public void issueNextInstruction() {

        InstItemList instruction;
        String op; //Operation to perform in the unit

        if (QueInst.size() <= count) {
            instNext = null;
            return;
        }
        instruction = QueInst.get(count); //get instruction from queue
        instNext = null;
        String s[] = instruction.Name.split("\\s+"); //// s[] stores the instruction in a format . s[0] - name , s[1] s[2] operands 
        op = s[0];
//If reservation location is free (no structural hazard), control issues instructions and sends operands 


        if (op.equals(insName[0]) || op.equals(insName[1]) || op.equals(insName[6]) || op.equals(insName[7])) {
            implementAddorSub(s, op, instruction);
        } else if (op.equals(insName[2]) || op.equals(insName[3]) || op.equals(insName[8])) {
            implementMULDorDIVD(s, op, instruction);
        } else if (op.equals(insName[4])) {

        	// transition to load
            instruction.operation = Const.LOAD;
            int des = Const.get2CharInt(s[1]);
            int src1 = Integer.parseInt(s[2]);
            instruction.tym = 2;
            instruction.source1 = src1;
            instruction.dest = des;

            int station = scheduleInstruction(Const.L); 

            for (int j = Const._S1; j <= Const._S3; j++) {
                if (queList.isBusy(j)) {
                    if (src1 == queList.getAddr(j)) {
                        return;
                    }
                }
            }

            instruction.position = station;

            reg_Float.setStation(instruction.dest, station);

            queList.setBusy(Const.getInstructionID(instruction.position));
            queList.setAddr(Const.getInstructionID(instruction.position), instruction.dest);

            count++;

        } else if (op.equals(insName[5])) {
// transition to store
            instruction.operation = Const.STORE;
            int src1 = Const.get2CharInt(s[1]);
            int des = Integer.parseInt(s[2]);
            instruction.tym = 2;
            instruction.source1 = src1;
            instruction.dest = des;


            int station = scheduleInstruction(Const.S);
            int k = Const._L1;
            while (k <= Const._L3) {
                if (queList.isBusy(k)) {
                    if (src1 == queList.getAddr(k)) {
                        return;
                    }
                }
                k++;
            }

            instruction.position = station;

            if (reg_Float.getStation(instruction.source1) != -1) {
                queList.setStation(Const.getInstructionID(instruction.position), reg_Float.getStation(instruction.source1));
            }
            queList.setBusy(Const.getInstructionID(instruction.position));
            queList.setAddr(Const.getInstructionID(instruction.position), instruction.dest);

            count++;


        }

        instNext = instruction;

        instruction.taskval = clk;


    }

    private void implementMULDorDIVD(String[] s, String op, InstItemList instruction) {
// for MULD and DIVD
// implementing operations of instructions of type MULD or DIVD
        int src1, src2, des, station;

        if (op.equals(insName[2])) {
            instruction.operation = Const.MULD;
            instruction.tym = 7;
             station = scheduleInstruction(Const.M);
        } else {
        	
            instruction.operation = Const.DIVD;
            instruction.tym = 25;
             station = scheduleInstruction(Const.D);
        }
        des = Const.get2CharInt(s[1]);
        src1 = Const.get2CharInt(s[2]);
        src2 = Const.get2CharInt(s[3]);
        instruction.source1 = src1;
        instruction.execute1 = src2;
        instruction.dest = des;

        

        if (station == -1) {
            return;
        }
        instruction.position = station;

        reg_Float.setStation(instruction.dest, instruction.position);
        resv_station.setBusy(Const.getInstructionID(instruction.position));
        resv_station.operVal(Const.getInstructionID(station), op);

        int src1Station = reg_Float.getStation(instruction.source1);
        int src2Station = reg_Float.getStation(instruction.execute1);

        if (src1Station == -1 || src1Station == station) {
            reg_Float.setStation(instruction.source1, -1);
            resv_station.setData1(Const.getInstructionID(station), reg_Float.Instr_read(instruction.source1));
        } else {
            resv_station.setLoc1(Const.getInstructionID(station), src1Station);
        }
        if (src2Station == -1 || src2Station == station) {
            reg_Float.setStation(instruction.execute1, -1);
            resv_station.setData2(Const.getInstructionID(station), reg_Float.Instr_read(instruction.execute1));
        } else {
            resv_station.setStation2(Const.getInstructionID(station), src2Station);
        }

        count++;

    }

    private void implementAddorSub(String s[], String op, InstItemList instruction) {
// implementing operations of instructions of type ADDD or SUBD
        if (op.equals(insName[0])) {
            instruction.operation = Const.ADDD;
        } else  if (op.equals(insName[1]))  {
            instruction.operation = Const.SUBD;
        }else  if (op.equals(insName[2]))  {
            instruction.operation = Const.DSUBD;
        }else  if (op.equals(insName[3]))  {
            instruction.operation = Const.DADDI;
        }
        else
        	instruction.operation = Const.DADDUI;
        
        int des = Const.get2CharInt(s[1]);
        int src1 = Const.get2CharInt(s[2]);
        int src2 = Const.get2CharInt(s[3]);
        instruction.source1 = src1;
        instruction.execute1 = src2;
        instruction.dest = des;
        instruction.tym = 2;

        int station = scheduleInstruction(Const.A);
        instruction.position = station;

        reg_Float.setStation(instruction.dest, instruction.position);
        resv_station.setBusy(Const.getInstructionID(instruction.position));
        resv_station.operVal(Const.getInstructionID(station), op);

        int src1Station = reg_Float.getStation(instruction.source1);
        int src2Station = reg_Float.getStation(instruction.execute1);
        if (src1Station == -1 || src1Station == station) {
            reg_Float.setStation(instruction.source1, -1);
            resv_station.setData1(Const.getInstructionID(station), reg_Float.Instr_read(instruction.source1));
        } else {
            resv_station.setLoc1(Const.getInstructionID(station), src1Station);
        }
        if (src2Station == -1 || src2Station == station) {
            reg_Float.setStation(instruction.execute1, -1);
            resv_station.setData2(Const.getInstructionID(station), reg_Float.Instr_read(instruction.execute1));
        } else {
            resv_station.setStation2(Const.getInstructionID(station), src2Station);
        }

        count++;
    }

    
    // get list of instructions that finished execution
    public void executeInstruction() {

        if (executVal.size() == 0) { // if it is 0 the return the value or linked list operation is performed for operations
            return;
        }
        
        LinkedList<Integer> tempList = new LinkedList<Integer>();

        for (int i = 0; i < executVal.size(); i++) {
            InstItemList instruction = executVal.get(i);

            switch (instruction.operation) {
            
           

                case Const.ADDD:
                case Const.DADDI:
                case Const.DADDUI:
                    if (instruction.tym == 4) {
                        if ((resv_station.getStation1(Const.getInstructionID(instruction.position)) == -1 || resv_station.getStation1(Const.getInstructionID(instruction.position)) == instruction.position)
                                && (resv_station.getStation2(Const.getInstructionID(instruction.position)) == -1 || resv_station.getStation2(Const.getInstructionID(instruction.position)) == instruction.position)) {
                            instruction.tym--;
                        }
                        break;
                    }
                    instruction.tym--;
                    if (instruction.tym == 0) {
                        float result = resv_station.getData1(Const.getInstructionID(instruction.position)) + resv_station.getData2(Const.getInstructionID(instruction.position));
                        nextWbList.add(instruction);

                        instruction.res = result;
                        instruction.execute = clk;

                        tempList.add(i);

                    }

                    break;
                    
              case Const.SUBD:
              case Const.DSUBD:
                    if (instruction.tym == 4) {
                        if ((resv_station.getStation1(Const.getInstructionID(instruction.position)) == -1 || resv_station.getStation1(Const.getInstructionID(instruction.position)) == instruction.position)
                                && (resv_station.getStation2(Const.getInstructionID(instruction.position)) == -1 || resv_station.getStation2(Const.getInstructionID(instruction.position)) == instruction.position)) {
                            instruction.tym--;
                        }
                        break;
                    }
                    instruction.tym--;
                    if (instruction.tym == 0) {
                        float result = resv_station.getData1(Const.getInstructionID(instruction.position)) - resv_station.getData2(Const.getInstructionID(instruction.position));
                        nextWbList.add(instruction);

                        instruction.res = result;
                        instruction.execute = clk;

                        tempList.add(i);

                    }
                    break; 
                case Const.MULD:


                    //  instruction time checking
                    if (instruction.tym == 7) {
                        if ((resv_station.getStation1(Const.getInstructionID(instruction.position)) == -1 || resv_station.getStation1(Const.getInstructionID(instruction.position)) == instruction.position)
                                && (resv_station.getStation2(Const.getInstructionID(instruction.position)) == -1 || resv_station.getStation2(Const.getInstructionID(instruction.position)) == instruction.position)) {
                            instruction.tym--;
                        }
                        break;
                    }
                    instruction.tym--;
                    if (instruction.tym == 0) {
                        float result = resv_station.getData1(Const.getInstructionID(instruction.position)) * resv_station.getData2(Const.getInstructionID(instruction.position));
                        nextWbList.add(instruction);

                        instruction.res = result;
                        instruction.execute = clk;

                        tempList.add(i);

                    }
                    break;
                case Const.DIVD:

                    //  instruction time checking
                    if (instruction.tym == 25) {
                        if ((resv_station.getStation1(Const.getInstructionID(instruction.position)) == -1 || resv_station.getStation1(Const.getInstructionID(instruction.position)) == instruction.position)
                                && (resv_station.getStation2(Const.getInstructionID(instruction.position)) == -1 || resv_station.getStation2(Const.getInstructionID(instruction.position)) == instruction.position)) {
                            instruction.tym--;
                        }
                        break;
                    }
                    instruction.tym--;
                    if (instruction.tym == 0) {
                        float result = resv_station.getData1(Const.getInstructionID(instruction.position)) / resv_station.getData2(Const.getInstructionID(instruction.position));
                        nextWbList.add(instruction);

                        instruction.res = result;
                        instruction.execute = clk;
                        tempList.add(i);

                    }
                    break;
                case Const.LOAD:


                    if (instruction.tym == 2) {
                        instruction.tym--;

                        break;
                    }
                    instruction.tym--;
                    if (instruction.tym == 0) {
                        float result = store.load(instruction.source1);
                        nextWbList.add(instruction);

                        instruction.res = result;
                        instruction.execute = clk;
                        tempList.add(i);
                    }

                    break;
                case Const.STORE:

                    if (instruction.tym == 2) {
                        if (queList.getStation(Const.getInstructionID(instruction.position)) == -1) {
                            instruction.tym--;
                        }

                        break;
                    }
                    instruction.tym--;
                    if (instruction.tym == 0) {
                        float result = reg_Float.Instr_read(instruction.source1);
                        nextWbList.add(instruction);

                        instruction.res = result;
                        instruction.execute = clk;
                        tempList.add(i);
                    }

                    break;

                default:
                    break;
            }
        }

        for (int i = tempList.size() - 1; i >= 0; i--) {
            executVal.remove((int) tempList.get(i));
        }


    }

    // assigning instructions to the register
    public int scheduleInstruction(int type) {

        int i, j;
        if (type == Const.L) {
            for (i = Const._L1, j = Const._L3; i <= j; i++) {
                if (!queList.isBusy(i)) {
                    return i + 11; //
                }
            }
        }
        if (type == Const.S) {
            for (i = Const._S1; i <= Const._S3; i++) {
                if (!queList.isBusy(i)) {
                    return i + 11;
                }
            }
        }
        if (type == Const.A) {
            for (i = Const._A1; i <= Const._A2; i++) { //
                if (!resv_station._isBusy(i)) {
                    return i + 17;
                }
            }
        }

        if (type == Const.M) {
            for (i = Const._M1; i <= Const._M2; i++) {
                if (!resv_station._isBusy(i)) {
                    return i + 17;
                }
            }
        }
        if (type == Const.D) {
            for (i = Const._D1; i <= Const._D2; i++) {
                if (!resv_station._isBusy(i)) {
                    return i + 17;
                }
            }
        }

        return -1;
    }

    public void writeback() {
		readVal();
	}

	public void readVal() { //Writes back on common data bus to all awaiting units; mark reservation loc available.

    	// mapping for associated value is ready..
    	
        LinkedList<Integer> tempList = new LinkedList<Integer>();

        for (int j = 0; j < resultList.size(); j++) {
            InstItemList instruction = resultList.get(j); // getting available instructions from list

            switch (instruction.operation) { 

                case Const.ADDD:
                case Const.SUBD:
                case Const.MULD:
                case Const.DIVD:
                case Const.DADDI:
                case Const.DADDUI:
                case Const.DSUBD:

                    resv_station.setIdle(Const.getInstructionID(instruction.position));
                    resv_station.setData1(Const.getInstructionID(instruction.position), 0);
                    resv_station.setData2(Const.getInstructionID(instruction.position), 0);
                    if (reg_Float.getStation(instruction.dest) == instruction.position) {
                        reg_Float.setStation(instruction.dest, -1);
                        reg_Float.Instr_write(instruction.dest, instruction.res);
                    }

                    for (int i = 0; i < Const.ResvNum; i++) {
                        if (resv_station.getStation1(i) == instruction.position) {
                            resv_station.setLoc1(i, -1);
                            resv_station.setData1(i, instruction.res);
                        }
                        if (resv_station.getStation2(i) == instruction.position) {
                            resv_station.setStation2(i, -1);
                            resv_station.setData2(i, instruction.res);
                        }

                    }
                    for (int i = 0; i < Const.ListQueNum; i++) {
                        if (queList.getStation(i) == instruction.position) {
                            queList.setStation(i, -1);
                        }
                    }
                    instruction.rewrite = clk;
                    break;

                case Const.LOAD:

                    queList.idleState(Const.getInstructionID(instruction.position));
                    queList.setAddr(Const.getInstructionID(instruction.position), -1);

                    if (reg_Float.getStation(instruction.dest) == instruction.position) {
                        reg_Float.setStation(instruction.dest, -1);
                        reg_Float.Instr_write(instruction.dest, instruction.res);
                    }

                    for (int i = 0; i < Const.ResvNum; i++) {
                        if (resv_station.getStation1(i) == instruction.position) {
                            resv_station.setLoc1(i, -1);
                            resv_station.setData1(i, instruction.res);
                        }
                        if (resv_station.getStation2(i) == instruction.position) {
                            resv_station.setStation2(i, -1);
                            resv_station.setData2(i, instruction.res);
                        }
                    }

                    for (int i = 0; i < Const.ListQueNum; i++) {

                        if (queList.getStation(i) == instruction.position) {
                            queList.setStation(i, -1);
                        }
                    }

                    instruction.rewrite = clk;

                    break;


                case Const.STORE:

                    queList.setAddr(Const.getInstructionID(instruction.position), -1);
                    store.setStore(instruction.dest, instruction.res);
                    queList.idleState(Const.getInstructionID(instruction.position));
                    instruction.rewrite = clk;
                    break;
            }
            tempList.add(j);
        }

        clearResultList(tempList);
    }

    private void clearResultList(LinkedList<Integer> tempList) {
        for (int i = tempList.size() - 1; i >= 0; i--) {
            resultList.remove((int) tempList.get(i));
        }
    }
}