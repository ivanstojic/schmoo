package com.ordecon.schmoo.smsc.connectors.smpp.pdu;
import com.ordecon.schmoo.smsc.connectors.smpp.pdu.BasicPDU;
import com.ordecon.schmoo.smsc.connectors.smpp.framework.SMPPInputStream;
import com.ordecon.schmoo.smsc.connectors.smpp.framework.SMPPOutputStream;

import java.io.*;

public class DeliverSmPDU extends BasicPDU {
    public DeliverSmPDU () {
    }

    public void read( SMPPInputStream in, int length ) throws IOException {
	int tag, len;

	/* Read mandatory parameters, one by one */
	m_mand_serviceType = in.readCString(false);
    length -= m_mand_serviceType.length()+1;
	m_mand_sourceAddrTon = in.readByte();
	length -= 1;
	m_mand_sourceAddrNpi = in.readByte();
	length -= 1;
	m_mand_sourceAddr = in.readCString(false);
    length -= m_mand_sourceAddr.length()+1;
	m_mand_destAddrTon = in.readByte();
	length -= 1;
	m_mand_destAddrNpi = in.readByte();
	length -= 1;
	m_mand_destinationAddr = in.readCString(false);
    length -= m_mand_destinationAddr.length()+1;
	m_mand_esmClass = in.readByte();
	length -= 1;
	m_mand_protocolId = in.readByte();
	length -= 1;
	m_mand_priorityFlag = in.readByte();
	length -= 1;
	m_mand_scheduleDeliveryTime = in.readCString(false);
    length -= m_mand_scheduleDeliveryTime.length()+1;
	m_mand_validityPeriod = in.readCString(false);
    length -= m_mand_validityPeriod.length()+1;
	m_mand_registeredDelivery = in.readByte();
	length -= 1;
	m_mand_replaceIfPresentFlag = in.readByte();
	length -= 1;
	m_mand_dataCoding = in.readByte();
	length -= 1;
	m_mand_smDefaultMsgId = in.readByte();
	length -= 1;
    m_mand_shortMessage = in.readPString(true);
    length -= m_mand_shortMessage.length()+1;

        //Log.getLog().logDebug("DeliverSmPDU", "Bytes left after reading mandatory parameters: " + length);

	/* Read optional parameters */
	while ( length>=4 ) {
	    tag = in.readShort();
	    len = in.readShort();
	    length -= 4 + len;

	    switch (tag) {
		/* stuff to generate a switch statement */
		case 0x0204:
		    m_opt_userMessageReference = in.readShort();
		    break;
		case 0x020A:
		    m_opt_sourcePort = in.readShort();
		    break;
		case 0x020B:
		    m_opt_destinationPort = in.readShort();
		    break;
		case 0x020C:
		    m_opt_sarMsgRefNum = in.readShort();
		    break;
		case 0x020E:
		    m_opt_sarTotalSegments = in.readByte();
		    break;
		case 0x020F:
		    m_opt_sarSegmentSeqnum = in.readByte();
		    break;
		case 0x0205:
		    m_opt_userResponseCode = in.readByte();
		    break;
		case 0x0201:
		    m_opt_privacyIndicator = in.readByte();
		    break;
		case 0x0019:
		    m_opt_payloadType = in.readByte();
		    break;
		case 0x0424:
		    m_opt_messagePayload = in.readCString(false);
		    break;
		case 0x0381:
		    m_opt_callbackNum = in.readCString(false);
		    break;
		case 0x0202:
		    m_opt_sourceSubaddress = in.readCString(false);
		    break;
		case 0x0203:
		    m_opt_destSubaddress = in.readCString(false);
		    break;
		case 0x020D:
		    m_opt_languageIndicator = in.readByte();
		    break;
		case 0x1383:
		    m_opt_itsSessionInfo = in.readShort();
		    break;
		case 0x0423:
		    m_opt_networkErrorCode = in.readCString(false);
		    break;
		case 0x0427:
		    m_opt_messageState = in.readByte();
		    break;
		case 0x001E:
		    m_opt_receiptedMessageId = in.readCString(false);
		    break;
		default:
		    /* skip_stuff(); */
		    in.skip( len );
	    }
	}

        if (length>0) {
            //Log.getLog().logDebug("DeliverSmPDU", "Skipping some crap in the SMPP stream...");
            in.skip(length);
        }
    }

    public void write( SMPPOutputStream out ) throws IOException {
	out.writeHeader( 0x00000005, getStatus(), getSequence() );
	/* Dumping mandatory parameters */
	out.writeCString( m_mand_serviceType, false);
	out.writeByte( m_mand_sourceAddrTon );
	out.writeByte( m_mand_sourceAddrNpi );
	out.writeCString( m_mand_sourceAddr, false);
	out.writeByte( m_mand_destAddrTon );
	out.writeByte( m_mand_destAddrNpi );
	out.writeCString( m_mand_destinationAddr, false);
	out.writeByte( m_mand_esmClass );
	out.writeByte( m_mand_protocolId );
	out.writeByte( m_mand_priorityFlag );
	out.writeCString( m_mand_scheduleDeliveryTime, false);
	out.writeCString( m_mand_validityPeriod, false);
	out.writeByte( m_mand_registeredDelivery );
	out.writeByte( m_mand_replaceIfPresentFlag );
	out.writeByte( m_mand_dataCoding );
	out.writeByte( m_mand_smDefaultMsgId );
	out.writePString( m_mand_shortMessage, false);

	/* And now, the optional parameters! */
	if ( !(m_opt_userMessageReference == UNDEFINED_VALUE) ) {
	    out.writeShort( 0x0204 );
	    out.writeShort(  2  );
	    out.writeShort( m_opt_userMessageReference );
	}
	if ( !(m_opt_sourcePort == UNDEFINED_VALUE) ) {
	    out.writeShort( 0x020A );
	    out.writeShort(  2  );
	    out.writeShort( m_opt_sourcePort );
	}
	if ( !(m_opt_destinationPort == UNDEFINED_VALUE) ) {
	    out.writeShort( 0x020B );
	    out.writeShort(  2  );
	    out.writeShort( m_opt_destinationPort );
	}
	if ( !(m_opt_sarMsgRefNum == UNDEFINED_VALUE) ) {
	    out.writeShort( 0x020C );
	    out.writeShort(  2  );
	    out.writeShort( m_opt_sarMsgRefNum );
	}
	if ( !(m_opt_sarTotalSegments == UNDEFINED_VALUE) ) {
	    out.writeShort( 0x020E );
	    out.writeShort(  1  );
	    out.writeByte( m_opt_sarTotalSegments );
	}
	if ( !(m_opt_sarSegmentSeqnum == UNDEFINED_VALUE) ) {
	    out.writeShort( 0x020F );
	    out.writeShort(  1  );
	    out.writeByte( m_opt_sarSegmentSeqnum );
	}
	if ( !(m_opt_userResponseCode == UNDEFINED_VALUE) ) {
	    out.writeShort( 0x0205 );
	    out.writeShort(  1  );
	    out.writeByte( m_opt_userResponseCode );
	}
	if ( !(m_opt_privacyIndicator == UNDEFINED_VALUE) ) {
	    out.writeShort( 0x0201 );
	    out.writeShort(  1  );
	    out.writeByte( m_opt_privacyIndicator );
	}
	if ( !(m_opt_payloadType == UNDEFINED_VALUE) ) {
	    out.writeShort( 0x0019 );
	    out.writeShort(  1  );
	    out.writeByte( m_opt_payloadType );
	}
	if ( !(m_opt_messagePayload.equals("")) ) {
	    out.writeShort( 0x0424 );
	    out.writeShort(  m_opt_messagePayload.length()+1  );
	    out.writeCString( m_opt_messagePayload, false);
	}
	if ( !(m_opt_callbackNum.equals("")) ) {
	    out.writeShort( 0x0381 );
	    out.writeShort(  m_opt_callbackNum.length()+1  );
	    out.writeCString( m_opt_callbackNum, false);
	}
	if ( !(m_opt_sourceSubaddress.equals("")) ) {
	    out.writeShort( 0x0202 );
	    out.writeShort(  m_opt_sourceSubaddress.length()+1  );
	    out.writeCString( m_opt_sourceSubaddress, false);
	}
	if ( !(m_opt_destSubaddress.equals("")) ) {
	    out.writeShort( 0x0203 );
	    out.writeShort(  m_opt_destSubaddress.length()+1  );
	    out.writeCString( m_opt_destSubaddress, false);
	}
	if ( !(m_opt_languageIndicator == UNDEFINED_VALUE) ) {
	    out.writeShort( 0x020D );
	    out.writeShort(  1  );
	    out.writeByte( m_opt_languageIndicator );
	}
	if ( !(m_opt_itsSessionInfo == UNDEFINED_VALUE) ) {
	    out.writeShort( 0x1383 );
	    out.writeShort(  2  );
	    out.writeShort( m_opt_itsSessionInfo );
	}
	if ( !(m_opt_networkErrorCode.equals("")) ) {
	    out.writeShort( 0x0423 );
	    out.writeShort(  m_opt_networkErrorCode.length()+1  );
	    out.writeCString( m_opt_networkErrorCode, false);
	}
	if ( !(m_opt_messageState == UNDEFINED_VALUE) ) {
	    out.writeShort( 0x0427 );
	    out.writeShort(  1  );
	    out.writeByte( m_opt_messageState );
	}
	if ( !(m_opt_receiptedMessageId.equals("")) ) {
	    out.writeShort( 0x001E );
	    out.writeShort(  m_opt_receiptedMessageId.length()+1  );
	    out.writeCString( m_opt_receiptedMessageId, false);
	}

    /* Finish writing the PDU. */
	out.endMessage();
    }

    public boolean equals ( Object o ) {
	if ( o instanceof DeliverSmPDU ) {
	    DeliverSmPDU p = (DeliverSmPDU) o;

	    /* Testing mandatory parameters for equality */
	    if ( !( m_mand_serviceType.equals(p.getServiceType())  ) ) { return false; }
	    if ( !( m_mand_sourceAddrTon == p.getSourceAddrTon()  ) ) { return false; }
	    if ( !( m_mand_sourceAddrNpi == p.getSourceAddrNpi()  ) ) { return false; }
	    if ( !( m_mand_sourceAddr.equals(p.getSourceAddr())  ) ) { return false; }
	    if ( !( m_mand_destAddrTon == p.getDestAddrTon()  ) ) { return false; }
	    if ( !( m_mand_destAddrNpi == p.getDestAddrNpi()  ) ) { return false; }
	    if ( !( m_mand_destinationAddr.equals(p.getDestinationAddr())  ) ) { return false; }
	    if ( !( m_mand_esmClass == p.getEsmClass()  ) ) { return false; }
	    if ( !( m_mand_protocolId == p.getProtocolId()  ) ) { return false; }
	    if ( !( m_mand_priorityFlag == p.getPriorityFlag()  ) ) { return false; }
	    if ( !( m_mand_scheduleDeliveryTime.equals(p.getScheduleDeliveryTime())  ) ) { return false; }
	    if ( !( m_mand_validityPeriod.equals(p.getValidityPeriod())  ) ) { return false; }
	    if ( !( m_mand_registeredDelivery == p.getRegisteredDelivery()  ) ) { return false; }
	    if ( !( m_mand_replaceIfPresentFlag == p.getReplaceIfPresentFlag()  ) ) { return false; }
	    if ( !( m_mand_dataCoding == p.getDataCoding()  ) ) { return false; }
	    if ( !( m_mand_smDefaultMsgId == p.getSmDefaultMsgId()  ) ) { return false; }
	    if ( !( m_mand_shortMessage.equals(p.getShortMessage()) ) ) { return false; }

	    /* Testing optional parameters for equality */
	    if ( !( m_opt_userMessageReference == p.getUserMessageReference()  ) ) { return false; }
	    if ( !( m_opt_sourcePort == p.getSourcePort()  ) ) { return false; }
	    if ( !( m_opt_destinationPort == p.getDestinationPort()  ) ) { return false; }
	    if ( !( m_opt_sarMsgRefNum == p.getSarMsgRefNum()  ) ) { return false; }
	    if ( !( m_opt_sarTotalSegments == p.getSarTotalSegments()  ) ) { return false; }
	    if ( !( m_opt_sarSegmentSeqnum == p.getSarSegmentSeqnum()  ) ) { return false; }
	    if ( !( m_opt_userResponseCode == p.getUserResponseCode()  ) ) { return false; }
	    if ( !( m_opt_privacyIndicator == p.getPrivacyIndicator()  ) ) { return false; }
	    if ( !( m_opt_payloadType == p.getPayloadType()  ) ) { return false; }
	    if ( !( m_opt_messagePayload.equals(p.getMessagePayload())  ) ) { return false; }
	    if ( !( m_opt_callbackNum.equals(p.getCallbackNum())  ) ) { return false; }
	    if ( !( m_opt_sourceSubaddress.equals(p.getSourceSubaddress())  ) ) { return false; }
	    if ( !( m_opt_destSubaddress.equals(p.getDestSubaddress())  ) ) { return false; }
	    if ( !( m_opt_languageIndicator == p.getLanguageIndicator()  ) ) { return false; }
	    if ( !( m_opt_itsSessionInfo == p.getItsSessionInfo()  ) ) { return false; }
	    if ( !( m_opt_networkErrorCode.equals(p.getNetworkErrorCode())  ) ) { return false; }
	    if ( !( m_opt_messageState == p.getMessageState()  ) ) { return false; }
	    if ( !( m_opt_receiptedMessageId.equals(p.getReceiptedMessageId())  ) ) { return false; }

	} else {
	    return false;
	}
	return true;
    }

    public String toString() {
	return "PDU: deliver_sm (0x00000005) seq " + getSequence() + "\n"
	    + "\tMandatory parameters:\n"
	    + "\t\tserviceType = "
	    + "\"" + m_mand_serviceType.toString() + "\""
	    + ", "
	    + "\t\tsourceAddrTon = "
	    + Integer.toString( m_mand_sourceAddrTon )
	    + ", "
	    + "\t\tsourceAddrNpi = "
	    + Integer.toString( m_mand_sourceAddrNpi )
	    + ", "
	    + "\t\tsourceAddr = "
	    + "\"" + m_mand_sourceAddr.toString() + "\""
	    + ", "
	    + "\t\tdestAddrTon = "
	    + Integer.toString( m_mand_destAddrTon )
	    + ", "
	    + "\t\tdestAddrNpi = "
	    + Integer.toString( m_mand_destAddrNpi )
	    + ", "
	    + "\t\tdestinationAddr = "
	    + "\"" + m_mand_destinationAddr.toString() + "\""
	    + ", "
	    + "\t\tesmClass = "
	    + Integer.toString( m_mand_esmClass )
	    + ", "
	    + "\t\tprotocolId = "
	    + Integer.toString( m_mand_protocolId )
	    + ", "
	    + "\t\tpriorityFlag = "
	    + Integer.toString( m_mand_priorityFlag )
	    + ", "
	    + "\t\tscheduleDeliveryTime = "
	    + "\"" + m_mand_scheduleDeliveryTime.toString() + "\""
	    + ", "
	    + "\t\tvalidityPeriod = "
	    + "\"" + m_mand_validityPeriod.toString() + "\""
	    + ", "
	    + "\t\tregisteredDelivery = "
	    + Integer.toString( m_mand_registeredDelivery )
	    + ", "
	    + "\t\treplaceIfPresentFlag = "
	    + Integer.toString( m_mand_replaceIfPresentFlag )
	    + ", "
	    + "\t\tdataCoding = "
	    + Integer.toString( m_mand_dataCoding )
	    + ", "
	    + "\t\tsmDefaultMsgId = "
	    + Integer.toString( m_mand_smDefaultMsgId )
	    + ", "
	    + "\t\tshortMessage = "
	    + "\"" + m_mand_shortMessage.toString() + "\""
	    + ", "

	    + "\tOptional parameters:\n"
	    + ( !( m_opt_userMessageReference == UNDEFINED_VALUE ) ?
		  "\t\tuserMessageReference = "
		+ Integer.toString( m_opt_userMessageReference ) + "\n"
	    : "" )
	    + ( !( m_opt_sourcePort == UNDEFINED_VALUE ) ?
		  "\t\tsourcePort = "
		+ Integer.toString( m_opt_sourcePort ) + "\n"
	    : "" )
	    + ( !( m_opt_destinationPort == UNDEFINED_VALUE ) ?
		  "\t\tdestinationPort = "
		+ Integer.toString( m_opt_destinationPort ) + "\n"
	    : "" )
	    + ( !( m_opt_sarMsgRefNum == UNDEFINED_VALUE ) ?
		  "\t\tsarMsgRefNum = "
		+ Integer.toString( m_opt_sarMsgRefNum ) + "\n"
	    : "" )
	    + ( !( m_opt_sarTotalSegments == UNDEFINED_VALUE ) ?
		  "\t\tsarTotalSegments = "
		+ Integer.toString( m_opt_sarTotalSegments ) + "\n"
	    : "" )
	    + ( !( m_opt_sarSegmentSeqnum == UNDEFINED_VALUE ) ?
		  "\t\tsarSegmentSeqnum = "
		+ Integer.toString( m_opt_sarSegmentSeqnum ) + "\n"
	    : "" )
	    + ( !( m_opt_userResponseCode == UNDEFINED_VALUE ) ?
		  "\t\tuserResponseCode = "
		+ Integer.toString( m_opt_userResponseCode ) + "\n"
	    : "" )
	    + ( !( m_opt_privacyIndicator == UNDEFINED_VALUE ) ?
		  "\t\tprivacyIndicator = "
		+ Integer.toString( m_opt_privacyIndicator ) + "\n"
	    : "" )
	    + ( !( m_opt_payloadType == UNDEFINED_VALUE ) ?
		  "\t\tpayloadType = "
		+ Integer.toString( m_opt_payloadType ) + "\n"
	    : "" )
	    + ( !m_opt_messagePayload.equals("") ?
		  "\t\tmessagePayload = "
		+ "\"" + m_opt_messagePayload.toString() + "\"\n"
	    : "" )
	    + ( !m_opt_callbackNum.equals("") ?
		  "\t\tcallbackNum = "
		+ "\"" + m_opt_callbackNum.toString() + "\"\n"
	    : "" )
	    + ( !m_opt_sourceSubaddress.equals("") ?
		  "\t\tsourceSubaddress = "
		+ "\"" + m_opt_sourceSubaddress.toString() + "\"\n"
	    : "" )
	    + ( !m_opt_destSubaddress.equals("") ?
		  "\t\tdestSubaddress = "
		+ "\"" + m_opt_destSubaddress.toString() + "\"\n"
	    : "" )
	    + ( !( m_opt_languageIndicator == UNDEFINED_VALUE ) ?
		  "\t\tlanguageIndicator = "
		+ Integer.toString( m_opt_languageIndicator ) + "\n"
	    : "" )
	    + ( !( m_opt_itsSessionInfo == UNDEFINED_VALUE ) ?
		  "\t\titsSessionInfo = "
		+ Integer.toString( m_opt_itsSessionInfo ) + "\n"
	    : "" )
	    + ( !m_opt_networkErrorCode.equals("") ?
		  "\t\tnetworkErrorCode = "
		+ "\"" + m_opt_networkErrorCode.toString() + "\"\n"
	    : "" )
	    + ( !( m_opt_messageState == UNDEFINED_VALUE ) ?
		  "\t\tmessageState = "
		+ Integer.toString( m_opt_messageState ) + "\n"
	    : "" )
	    + ( !m_opt_receiptedMessageId.equals("") ?
		  "\t\treceiptedMessageId = "
		+ "\"" + m_opt_receiptedMessageId.toString() + "\"\n"
	    : "" )
	;
    }


    /* Parameter getters */
    public String getServiceType () {
	return m_mand_serviceType;
    }
    public int getSourceAddrTon () {
	return m_mand_sourceAddrTon;
    }
    public int getSourceAddrNpi () {
	return m_mand_sourceAddrNpi;
    }
    public String getSourceAddr () {
	return m_mand_sourceAddr;
    }
    public int getDestAddrTon () {
	return m_mand_destAddrTon;
    }
    public int getDestAddrNpi () {
	return m_mand_destAddrNpi;
    }
    public String getDestinationAddr () {
	return m_mand_destinationAddr;
    }
    public int getEsmClass () {
	return m_mand_esmClass;
    }
    public int getProtocolId () {
	return m_mand_protocolId;
    }
    public int getPriorityFlag () {
	return m_mand_priorityFlag;
    }
    public String getScheduleDeliveryTime () {
	return m_mand_scheduleDeliveryTime;
    }
    public String getValidityPeriod () {
	return m_mand_validityPeriod;
    }
    public int getRegisteredDelivery () {
	return m_mand_registeredDelivery;
    }
    public int getReplaceIfPresentFlag () {
	return m_mand_replaceIfPresentFlag;
    }
    public int getDataCoding () {
	return m_mand_dataCoding;
    }
    public int getSmDefaultMsgId () {
	return m_mand_smDefaultMsgId;
    }
    public String getShortMessage () {
	return m_mand_shortMessage;
    }
    public int getUserMessageReference () {
	return m_opt_userMessageReference;
    }
    public int getSourcePort () {
	return m_opt_sourcePort;
    }
    public int getDestinationPort () {
	return m_opt_destinationPort;
    }
    public int getSarMsgRefNum () {
	return m_opt_sarMsgRefNum;
    }
    public int getSarTotalSegments () {
	return m_opt_sarTotalSegments;
    }
    public int getSarSegmentSeqnum () {
	return m_opt_sarSegmentSeqnum;
    }
    public int getUserResponseCode () {
	return m_opt_userResponseCode;
    }
    public int getPrivacyIndicator () {
	return m_opt_privacyIndicator;
    }
    public int getPayloadType () {
	return m_opt_payloadType;
    }
    public String getMessagePayload () {
	return m_opt_messagePayload;
    }
    public String getCallbackNum () {
	return m_opt_callbackNum;
    }
    public String getSourceSubaddress () {
	return m_opt_sourceSubaddress;
    }
    public String getDestSubaddress () {
	return m_opt_destSubaddress;
    }
    public int getLanguageIndicator () {
	return m_opt_languageIndicator;
    }
    public int getItsSessionInfo () {
	return m_opt_itsSessionInfo;
    }
    public String getNetworkErrorCode () {
	return m_opt_networkErrorCode;
    }
    public int getMessageState () {
	return m_opt_messageState;
    }
    public String getReceiptedMessageId () {
	return m_opt_receiptedMessageId;
    }


    /* Parameter setters */
    public void setServiceType ( String serviceType ) {
	m_mand_serviceType = serviceType;
    }
    public void setSourceAddrTon ( int sourceAddrTon ) {
	m_mand_sourceAddrTon = sourceAddrTon;
    }
    public void setSourceAddrNpi ( int sourceAddrNpi ) {
	m_mand_sourceAddrNpi = sourceAddrNpi;
    }
    public void setSourceAddr ( String sourceAddr ) {
	m_mand_sourceAddr = sourceAddr;
    }
    public void setDestAddrTon ( int destAddrTon ) {
	m_mand_destAddrTon = destAddrTon;
    }
    public void setDestAddrNpi ( int destAddrNpi ) {
	m_mand_destAddrNpi = destAddrNpi;
    }
    public void setDestinationAddr ( String destinationAddr ) {
	m_mand_destinationAddr = destinationAddr;
    }
    public void setEsmClass ( int esmClass ) {
	m_mand_esmClass = esmClass;
    }
    public void setProtocolId ( int protocolId ) {
	m_mand_protocolId = protocolId;
    }
    public void setPriorityFlag ( int priorityFlag ) {
	m_mand_priorityFlag = priorityFlag;
    }
    public void setScheduleDeliveryTime ( String scheduleDeliveryTime ) {
	m_mand_scheduleDeliveryTime = scheduleDeliveryTime;
    }
    public void setValidityPeriod ( String validityPeriod ) {
	m_mand_validityPeriod = validityPeriod;
    }
    public void setRegisteredDelivery ( int registeredDelivery ) {
	m_mand_registeredDelivery = registeredDelivery;
    }
    public void setReplaceIfPresentFlag ( int replaceIfPresentFlag ) {
	m_mand_replaceIfPresentFlag = replaceIfPresentFlag;
    }
    public void setDataCoding ( int dataCoding ) {
	m_mand_dataCoding = dataCoding;
    }
    public void setSmDefaultMsgId ( int smDefaultMsgId ) {
	m_mand_smDefaultMsgId = smDefaultMsgId;
    }
    public void setShortMessage ( String shortMessage ) {
	m_mand_shortMessage = shortMessage;
    }
    public void setUserMessageReference ( int userMessageReference) {
	m_opt_userMessageReference = userMessageReference;
    }
    public void setSourcePort ( int sourcePort) {
	m_opt_sourcePort = sourcePort;
    }
    public void setDestinationPort ( int destinationPort) {
	m_opt_destinationPort = destinationPort;
    }
    public void setSarMsgRefNum ( int sarMsgRefNum) {
	m_opt_sarMsgRefNum = sarMsgRefNum;
    }
    public void setSarTotalSegments ( int sarTotalSegments) {
	m_opt_sarTotalSegments = sarTotalSegments;
    }
    public void setSarSegmentSeqnum ( int sarSegmentSeqnum) {
	m_opt_sarSegmentSeqnum = sarSegmentSeqnum;
    }
    public void setUserResponseCode ( int userResponseCode) {
	m_opt_userResponseCode = userResponseCode;
    }
    public void setPrivacyIndicator ( int privacyIndicator) {
	m_opt_privacyIndicator = privacyIndicator;
    }
    public void setPayloadType ( int payloadType) {
	m_opt_payloadType = payloadType;
    }
    public void setMessagePayload ( String messagePayload) {
	m_opt_messagePayload = messagePayload;
    }
    public void setCallbackNum ( String callbackNum) {
	m_opt_callbackNum = callbackNum;
    }
    public void setSourceSubaddress ( String sourceSubaddress) {
	m_opt_sourceSubaddress = sourceSubaddress;
    }
    public void setDestSubaddress ( String destSubaddress) {
	m_opt_destSubaddress = destSubaddress;
    }
    public void setLanguageIndicator ( int languageIndicator) {
	m_opt_languageIndicator = languageIndicator;
    }
    public void setItsSessionInfo ( int itsSessionInfo) {
	m_opt_itsSessionInfo = itsSessionInfo;
    }
    public void setNetworkErrorCode ( String networkErrorCode) {
	m_opt_networkErrorCode = networkErrorCode;
    }
    public void setMessageState ( int messageState) {
	m_opt_messageState = messageState;
    }
    public void setReceiptedMessageId ( String receiptedMessageId) {
	m_opt_receiptedMessageId = receiptedMessageId;
    }


    /* Constant values */
    int UNDEFINED_VALUE = 0;

    /* Define private variables - mandatory params*/
    private String  m_mand_serviceType = "";
    private int  m_mand_sourceAddrTon = UNDEFINED_VALUE;
    private int  m_mand_sourceAddrNpi = UNDEFINED_VALUE;
    private String  m_mand_sourceAddr = "";
    private int  m_mand_destAddrTon = UNDEFINED_VALUE;
    private int  m_mand_destAddrNpi = UNDEFINED_VALUE;
    private String  m_mand_destinationAddr = "";
    private int  m_mand_esmClass = UNDEFINED_VALUE;
    private int  m_mand_protocolId = UNDEFINED_VALUE;
    private int  m_mand_priorityFlag = UNDEFINED_VALUE;
    private String  m_mand_scheduleDeliveryTime = "";
    private String  m_mand_validityPeriod = "";
    private int  m_mand_registeredDelivery = UNDEFINED_VALUE;
    private int  m_mand_replaceIfPresentFlag = UNDEFINED_VALUE;
    private int  m_mand_dataCoding = UNDEFINED_VALUE;
    private int  m_mand_smDefaultMsgId = UNDEFINED_VALUE;
    private String  m_mand_shortMessage = "";

    /* Define private variables - optional params */
    private int  m_opt_userMessageReference = UNDEFINED_VALUE;
    private int  m_opt_sourcePort = UNDEFINED_VALUE;
    private int  m_opt_destinationPort = UNDEFINED_VALUE;
    private int  m_opt_sarMsgRefNum = UNDEFINED_VALUE;
    private int  m_opt_sarTotalSegments = UNDEFINED_VALUE;
    private int  m_opt_sarSegmentSeqnum = UNDEFINED_VALUE;
    private int  m_opt_userResponseCode = UNDEFINED_VALUE;
    private int  m_opt_privacyIndicator = UNDEFINED_VALUE;
    private int  m_opt_payloadType = UNDEFINED_VALUE;
    private String  m_opt_messagePayload = "";
    private String  m_opt_callbackNum = "";
    private String  m_opt_sourceSubaddress = "";
    private String  m_opt_destSubaddress = "";
    private int  m_opt_languageIndicator = UNDEFINED_VALUE;
    private int  m_opt_itsSessionInfo = UNDEFINED_VALUE;
    private String  m_opt_networkErrorCode = "";
    private int  m_opt_messageState = UNDEFINED_VALUE;
    private String  m_opt_receiptedMessageId = "";
}
