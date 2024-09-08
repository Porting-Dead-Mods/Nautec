package com.portingdeadmods.modjam.capabilities.augmentation;

public enum Slot {
        HEAD(0),
        BODY(1),
        ARMS(2),
        LEGS(3),
        HEART(4),
        NONE(-1);

        public int slotId;
        Slot(int id){
                this.slotId = id;
        }
        public boolean Compare(int i){return slotId == i;}
        public static Slot GetValue(int _id)
        {
                Slot[] Slots = Slot.values();
                for(int i = 0; i < Slots.length; i++)
                {
                        if(Slots[i].Compare(_id))
                                return Slots[i];
                }
                return Slot.NONE;
        }
}
