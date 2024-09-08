package com.portingdeadmods.modjam.capabilities.augmentation;

public enum Slot {
        HEAD(0),
        BODY(1),
        ARMS(2),
        LEGS(3),
        HEART(4);

        public int slotId;
        Slot(int id){
                this.slotId = id;
        }
}
