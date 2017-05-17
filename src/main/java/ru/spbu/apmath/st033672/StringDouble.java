package ru.spbu.apmath.st033672;

import org.apache.hadoop.io.Writable;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by henry on 4/30/17.
 */
public class StringDouble implements Writable, Serializable {


    private String string;
    private double value;

    public StringDouble() {
    }

    public StringDouble(String docName, double value) {
        this.string = docName;
        this.value = value;
    }

    public StringDouble(StringDouble that){
        this.string = that.string;
        this.value = that.value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(string);
        dataOutput.writeDouble(value);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        string = dataInput.readUTF();
        value = dataInput.readDouble();
    }

    @Override
    public String toString() {
        return string + "\t" + value;
    }
}
