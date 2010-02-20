package com.ordecon.schmoo.smsc.connectors.smpp.pdu;
import com.ordecon.schmoo.smsc.connectors.smpp.pdu.BasicPDU;
import com.ordecon.schmoo.smsc.connectors.smpp.framework.SMPPInputStream;
import com.ordecon.schmoo.smsc.connectors.smpp.framework.SMPPOutputStream;
import java.io.*;

public class SubmitSmPDU extends BasicPDU {
    public SubmitSmPDU () {
    }

    public void read( SMPPInputStream in, int length ) throws IOException {
	int tag, len;

	/* Read mandatory parameters, one by one */
	m_mand_serviceType = in.readCString(6, false);
    length -= m_mand_serviceType.length()+1;
	m_mand_sourceAddrTon = in.readByte();
	length -= 1;
	m_mand_sourceAddrNpi = in.readByte();
	length -= 1;
	m_mand_sourceAddr = in.readCString(21, false);
    length -= m_mand_sourceAddr.length()+1;
	m_mand_destAddrTon = in.readByte();
	length -= 1;
	m_mand_destAddrNpi = in.readByte();
	length -= 1;
	m_mand_destinationAddr = in.readCString(21, false);
    length -= m_mand_destinationAddr.length()+1;
	m_mand_esmClass = in.readByte();
	length -= 1;
	m_mand_protocolId = in.readByte();
	length -= 1;
	m_mand_priorityFlag = in.readByte();
	length -= 1;
	m_mand_scheduleDeliveryTime = in.readCString(17, false);
    length -= m_mand_scheduleDeliveryTime.length()+1;
	m_mand_validityPeriod = in.readCString(17, false);
    length -= m_mand_validityPeriod.length()+1;
	m_mand_registeredDelivery = in.readByte();
	length -= 1;
	m_mand_replaceIfPresent = in.readByte();
	length -= 1;
	m_mand_dataCoding = in.readByte();
	length -= 1;
	m_mand_smDefaultMsgId = in.readByte();
	length -= 1;
    m_mand_shortMessage = in.readPString(true);
    length -= m_mand_shortMessage.length()+1;

	/* Read optional parameters */
	while ( length>0 ) {
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
		case 0x000D:
		    m_opt_sourceAddrSubunit = in.readByte();
		    break;
		case 0x0005:
		    m_opt_destAddrSubunit = in.readByte();
		    break;
		case 0x0426:
		    m_opt_moreMessagesToSend = in.readByte();
		    break;
		case 0x0302:
		    m_opt_callbackNumPresInd = in.readByte();
		    break;
		case 0x0303:
		    m_opt_callbackNumAtag = in.readCString(false);
		    break;
		case 0x1201:
		    m_opt_displayTime = in.readByte();
		    break;
		case 0x1203:
		    m_opt_smsSignal = in.readShort();
		    break;
		case 0x1204:
		    m_opt_msValidity = in.readByte();
		    break;
		case 0x0030:
		    m_opt_msMsgWaitFacilities = in.readByte();
		    break;
		case 0x0304:
		    m_opt_numberOfMessages = in.readByte();
		    break;
		case 0x130C:
		    m_opt_alertOnMsgDelivery = in.readByte();
		    break;
		case 0x1380:
		    m_opt_itsReplyType = in.readByte();
		    break;
		case 0x0501:
		    m_opt_ussdServiceOp = in.readByte();
		    break;
		default:
		    /* skip_stuff(); */
		    in.skip( len );
	    }
	}
    }

    public void write( SMPPOutputStream out ) throws IOException {
	out.writeHeader( 0x00000004, getStatus(), getSequence() );
	/* Dumping mandatory parameters */
	out.writeCString( m_mand_serviceType, false);
	out.writeByte( m_mand_sourceAddrTon );
	out.writeByte( m_mand_sourceAddrNpi );
	out.writeCString( m_mand_sourceAddr, true);
	out.writeByte( m_mand_destAddrTon );
	out.writeByte( m_mand_destAddrNpi );
	out.writeCString( m_mand_destinationAddr, false);
	out.writeByte( m_mand_esmClass );
	out.writeByte( m_mand_protocolId );
	out.writeByte( m_mand_priorityFlag );
	out.writeCString( m_mand_scheduleDeliveryTime, false);
	out.writeCString( m_mand_validityPeriod, false);
	out.writeByte( m_mand_registeredDelivery );
	out.writeByte( m_mand_replaceIfPresent );
	out.writeByte( m_mand_dataCoding );
	out.writeByte( m_mand_smDefaultMsgId );
	out.writePString( m_mand_shortMessage, (m_mand_dataCoding==4) ? false : true);

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
	if ( !(m_opt_sourceAddrSubunit == UNDEFINED_VALUE) ) {
	    out.writeShort( 0x000D );
	    out.writeShort(  1  );
	    out.writeByte( m_opt_sourceAddrSubunit );
	}
	if ( !(m_opt_destAddrSubunit == UNDEFINED_VALUE) ) {
	    out.writeShort( 0x0005 );
	    out.writeShort(  1  );
	    out.writeByte( m_opt_destAddrSubunit );
	}
	if ( !(m_opt_moreMessagesToSend == UNDEFINED_VALUE) ) {
	    out.writeShort( 0x0426 );
	    out.writeShort(  1  );
	    out.writeByte( m_opt_moreMessagesToSend );
	}
	if ( !(m_opt_callbackNumPresInd == UNDEFINED_VALUE) ) {
	    out.writeShort( 0x0302 );
	    out.writeShort(  1  );
	    out.writeByte( m_opt_callbackNumPresInd );
	}
	if ( !(m_opt_callbackNumAtag.equals("")) ) {
	    out.writeShort( 0x0303 );
	    out.writeShort(  m_opt_callbackNumAtag.length()+1  );
	    out.writeCString( m_opt_callbackNumAtag, false);
	}
	if ( !(m_opt_displayTime == UNDEFINED_VALUE) ) {
	    out.writeShort( 0x1201 );
	    out.writeShort(  1  );
	    out.writeByte( m_opt_displayTime );
	}
	if ( !(m_opt_smsSignal == UNDEFINED_VALUE) ) {
	    out.writeShort( 0x1203 );
	    out.writeShort(  2  );
	    out.writeShort( m_opt_smsSignal );
	}
	if ( !(m_opt_msValidity == UNDEFINED_VALUE) ) {
	    out.writeShort( 0x1204 );
	    out.writeShort(  1  );
	    out.writeByte( m_opt_msValidity );
	}
	if ( !(m_opt_msMsgWaitFacilities == UNDEFINED_VALUE) ) {
	    out.writeShort( 0x0030 );
	    out.writeShort(  1  );
	    out.writeByte( m_opt_msMsgWaitFacilities );
	}
	if ( !(m_opt_numberOfMessages == UNDEFINED_VALUE) ) {
	    out.writeShort( 0x0304 );
	    out.writeShort(  1  );
	    out.writeByte( m_opt_numberOfMessages );
	}
	if ( !(m_opt_alertOnMsgDelivery == UNDEFINED_VALUE) ) {
	    out.writeShort( 0x130C );
	    out.writeShort(  1  );
	    out.writeByte( m_opt_alertOnMsgDelivery );
	}
	if ( !(m_opt_itsReplyType == UNDEFINED_VALUE) ) {
	    out.writeShort( 0x1380 );
	    out.writeShort(  1  );
	    out.writeByte( m_opt_itsReplyType );
	}
	if ( !(m_opt_ussdServiceOp == UNDEFINED_VALUE) ) {
	    out.writeShort( 0x0501 );
	    out.writeShort(  1  );
	    out.writeByte( m_opt_ussdServiceOp );
	}

    /* Finish writing the PDU. */
	out.endMessage();
    }

    public boolean equals ( Object o ) {
	if ( o instanceof SubmitSmPDU ) {
	    SubmitSmPDU p = (SubmitSmPDU) o;

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
	    if ( !( m_mand_replaceIfPresent == p.getReplaceIfPresent()  ) ) { return false; }
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
	    if ( !( m_opt_sourceAddrSubunit == p.getSourceAddrSubunit()  ) ) { return false; }
	    if ( !( m_opt_destAddrSubunit == p.getDestAddrSubunit()  ) ) { return false; }
	    if ( !( m_opt_moreMessagesToSend == p.getMoreMessagesToSend()  ) ) { return false; }
	    if ( !( m_opt_callbackNumPresInd == p.getCallbackNumPresInd()  ) ) { return false; }
	    if ( !( m_opt_callbackNumAtag.equals(p.getCallbackNumAtag())  ) ) { return false; }
	    if ( !( m_opt_displayTime == p.getDisplayTime()  ) ) { return false; }
	    if ( !( m_opt_smsSignal == p.getSmsSignal()  ) ) { return false; }
	    if ( !( m_opt_msValidity == p.getMsValidity()  ) ) { return false; }
	    if ( !( m_opt_msMsgWaitFacilities == p.getMsMsgWaitFacilities()  ) ) { return false; }
	    if ( !( m_opt_numberOfMessages == p.getNumberOfMessages()  ) ) { return false; }
	    if ( !( m_opt_alertOnMsgDelivery == p.getAlertOnMsgDelivery()  ) ) { return false; }
	    if ( !( m_opt_itsReplyType == p.getItsReplyType()  ) ) { return false; }
	    if ( !( m_opt_ussdServiceOp == p.getUssdServiceOp()  ) ) { return false; }

	} else {
	    return false;
	}
	return true;
    }

    public String toString() {
	return "PDU: submit_sm (0x00000004) seq " + getSequence() + "\n"
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
	    + "\t\treplaceIfPresent = "
	    + Integer.toString( m_mand_replaceIfPresent )
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
	    + ( !( m_opt_sourceAddrSubunit == UNDEFINED_VALUE ) ?
		  "\t\tsourceAddrSubunit = "
		+ Integer.toString( m_opt_sourceAddrSubunit ) + "\n"
	    : "" )
	    + ( !( m_opt_destAddrSubunit == UNDEFINED_VALUE ) ?
		  "\t\tdestAddrSubunit = "
		+ Integer.toString( m_opt_destAddrSubunit ) + "\n"
	    : "" )
	    + ( !( m_opt_moreMessagesToSend == UNDEFINED_VALUE ) ?
		  "\t\tmoreMessagesToSend = "
		+ Integer.toString( m_opt_moreMessagesToSend ) + "\n"
	    : "" )
	    + ( !( m_opt_callbackNumPresInd == UNDEFINED_VALUE ) ?
		  "\t\tcallbackNumPresInd = "
		+ Integer.toString( m_opt_callbackNumPresInd ) + "\n"
	    : "" )
	    + ( !m_opt_callbackNumAtag.equals("") ?
		  "\t\tcallbackNumAtag = "
		+ "\"" + m_opt_callbackNumAtag.toString() + "\"\n"
	    : "" )
	    + ( !( m_opt_displayTime == UNDEFINED_VALUE ) ?
		  "\t\tdisplayTime = "
		+ Integer.toString( m_opt_displayTime ) + "\n"
	    : "" )
	    + ( !( m_opt_smsSignal == UNDEFINED_VALUE ) ?
		  "\t\tsmsSignal = "
		+ Integer.toString( m_opt_smsSignal ) + "\n"
	    : "" )
	    + ( !( m_opt_msValidity == UNDEFINED_VALUE ) ?
		  "\t\tmsValidity = "
		+ Integer.toString( m_opt_msValidity ) + "\n"
	    : "" )
	    + ( !( m_opt_msMsgWaitFacilities == UNDEFINED_VALUE ) ?
		  "\t\tmsMsgWaitFacilities = "
		+ Integer.toString( m_opt_msMsgWaitFacilities ) + "\n"
	    : "" )
	    + ( !( m_opt_numberOfMessages == UNDEFINED_VALUE ) ?
		  "\t\tnumberOfMessages = "
		+ Integer.toString( m_opt_numberOfMessages ) + "\n"
	    : "" )
	    + ( !( m_opt_alertOnMsgDelivery == UNDEFINED_VALUE ) ?
		  "\t\talertOnMsgDelivery = "
		+ Integer.toString( m_opt_alertOnMsgDelivery ) + "\n"
	    : "" )
	    + ( !( m_opt_itsReplyType == UNDEFINED_VALUE ) ?
		  "\t\titsReplyType = "
		+ Integer.toString( m_opt_itsReplyType ) + "\n"
	    : "" )
	    + ( !( m_opt_ussdServiceOp == UNDEFINED_VALUE ) ?
		  "\t\tussdServiceOp = "
		+ Integer.toString( m_opt_ussdServiceOp ) + "\n"
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
    public int getReplaceIfPresent () {
	return m_mand_replaceIfPresent;
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
    public int getSourceAddrSubunit () {
	return m_opt_sourceAddrSubunit;
    }
    public int getDestAddrSubunit () {
	return m_opt_destAddrSubunit;
    }
    public int getMoreMessagesToSend () {
	return m_opt_moreMessagesToSend;
    }
    public int getCallbackNumPresInd () {
	return m_opt_callbackNumPresInd;
    }
    public String getCallbackNumAtag () {
	return m_opt_callbackNumAtag;
    }
    public int getDisplayTime () {
	return m_opt_displayTime;
    }
    public int getSmsSignal () {
	return m_opt_smsSignal;
    }
    public int getMsValidity () {
	return m_opt_msValidity;
    }
    public int getMsMsgWaitFacilities () {
	return m_opt_msMsgWaitFacilities;
    }
    public int getNumberOfMessages () {
	return m_opt_numberOfMessages;
    }
    public int getAlertOnMsgDelivery () {
	return m_opt_alertOnMsgDelivery;
    }
    public int getItsReplyType () {
	return m_opt_itsReplyType;
    }
    public int getUssdServiceOp () {
	return m_opt_ussdServiceOp;
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
    public void setReplaceIfPresent ( int replaceIfPresent ) {
	m_mand_replaceIfPresent = replaceIfPresent;
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
    public void setSourceAddrSubunit ( int sourceAddrSubunit) {
	m_opt_sourceAddrSubunit = sourceAddrSubunit;
    }
    public void setDestAddrSubunit ( int destAddrSubunit) {
	m_opt_destAddrSubunit = destAddrSubunit;
    }
    public void setMoreMessagesToSend ( int moreMessagesToSend) {
	m_opt_moreMessagesToSend = moreMessagesToSend;
    }
    public void setCallbackNumPresInd ( int callbackNumPresInd) {
	m_opt_callbackNumPresInd = callbackNumPresInd;
    }
    public void setCallbackNumAtag ( String callbackNumAtag) {
	m_opt_callbackNumAtag = callbackNumAtag;
    }
    public void setDisplayTime ( int displayTime) {
	m_opt_displayTime = displayTime;
    }
    public void setSmsSignal ( int smsSignal) {
	m_opt_smsSignal = smsSignal;
    }
    public void setMsValidity ( int msValidity) {
	m_opt_msValidity = msValidity;
    }
    public void setMsMsgWaitFacilities ( int msMsgWaitFacilities) {
	m_opt_msMsgWaitFacilities = msMsgWaitFacilities;
    }
    public void setNumberOfMessages ( int numberOfMessages) {
	m_opt_numberOfMessages = numberOfMessages;
    }
    public void setAlertOnMsgDelivery ( int alertOnMsgDelivery) {
	m_opt_alertOnMsgDelivery = alertOnMsgDelivery;
    }
    public void setItsReplyType ( int itsReplyType) {
	m_opt_itsReplyType = itsReplyType;
    }
    public void setUssdServiceOp ( int ussdServiceOp) {
	m_opt_ussdServiceOp = ussdServiceOp;
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
    private int  m_mand_replaceIfPresent = UNDEFINED_VALUE;
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
    private int  m_opt_sourceAddrSubunit = UNDEFINED_VALUE;
    private int  m_opt_destAddrSubunit = UNDEFINED_VALUE;
    private int  m_opt_moreMessagesToSend = UNDEFINED_VALUE;
    private int  m_opt_callbackNumPresInd = UNDEFINED_VALUE;
    private String  m_opt_callbackNumAtag = "";
    private int  m_opt_displayTime = UNDEFINED_VALUE;
    private int  m_opt_smsSignal = UNDEFINED_VALUE;
    private int  m_opt_msValidity = UNDEFINED_VALUE;
    private int  m_opt_msMsgWaitFacilities = UNDEFINED_VALUE;
    private int  m_opt_numberOfMessages = UNDEFINED_VALUE;
    private int  m_opt_alertOnMsgDelivery = UNDEFINED_VALUE;
    private int  m_opt_itsReplyType = UNDEFINED_VALUE;
    private int  m_opt_ussdServiceOp = UNDEFINED_VALUE;
}
