package com.ordecon.schmoo.smsc.connectors.smpp.pdu;

import java.io.IOException;
import com.ordecon.schmoo.smsc.connectors.smpp.framework.*;
import com.ordecon.schmoo.smsc.connectors.smpp.utils.StatusText;

public class BasicPDU {
    private int m_id;
    private int m_status;
    private int m_sequence;
    protected int m_length;

    public void read(SMPPInputStream in, int length) throws IOException {
        in.skip(length);
        throw new IOException("Can't read BasicPDU! id="+m_id+" seq="+m_sequence+" m_status="+StatusText.getStatusText(m_status)+" length="+m_length);
    }



    public void write(SMPPOutputStream out) throws IOException {
        throw new IOException("Can't write BasicPDU!");
    }



    public int getId() {
        return m_id;
    }



    public void setId(int m_id) {
        this.m_id = m_id;
    }



    public int getStatus() {
        return m_status;
    }



    public void setStatus(int m_status) {
        this.m_status = m_status;
    }



    public int getSequence() {
        return m_sequence;
    }



    public void setSequence(int m_sequence) {
        this.m_sequence = m_sequence;
    }

    public int getLength() {
        return m_length;
    }

    public void setLength(int m_length) {
        this.m_length = m_length;
    }
}



