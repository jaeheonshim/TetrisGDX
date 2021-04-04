package com.jaeheonshim.tetris.util;

import java.util.Random;

public class ObfuscatedMemoryInteger {
    private Random random = new Random();

    private int obfuscatedValue;
    private int obfuscator;
    private int checksum;

    private int checksumMultiplier = random.nextInt();

    public ObfuscatedMemoryInteger() {

    }

    public ObfuscatedMemoryInteger(int value) {
        set(value);
    }

    private int generateChecksum() {
        return (obfuscatedValue * checksumMultiplier) % Integer.MAX_VALUE;
    }

    public void set(int value) {
        obfuscator = random.nextInt();
        obfuscatedValue = value ^ obfuscator;
        this.checksum = generateChecksum();
    }

    public int get() {
        int previousChecksum = generateChecksum();
        if(checksum != previousChecksum) {
            throw new RuntimeException("Immutable integer in ObfuscatedMemoryInteger was modified: Possible unauthorized memory modification");
        }

        return obfuscatedValue ^ obfuscator;
    }
}
