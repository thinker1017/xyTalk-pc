package xysoft.im.service;

import org.jivesoftware.smack.provider.ProviderManager;
import xysoft.im.extension.Features;
import xysoft.im.extension.MucInvitation;
import xysoft.im.extension.OfflineFile;
import xysoft.im.extension.OfflineFileReceipt;

public class ProviderRegister {

	public ProviderRegister() {
	}
	
	public static void register(){
		//ProviderManager.addExtensionProvider("request", "urn:xmpp:receipts", new ReceiptProvider());
        ProviderManager.addExtensionProvider("event", "http://jabber.org/protocol/disco#info", new Features.Provider());
        ProviderManager.addExtensionProvider("x", MucInvitation.NAMESPACE, new MucInvitation.Provider());
        ProviderManager.addExtensionProvider("x", OfflineFile.NAMESPACE, new OfflineFile.Provider());
        ProviderManager.addExtensionProvider("x", OfflineFileReceipt.NAMESPACE, new OfflineFileReceipt.Provider());        
	}

}
