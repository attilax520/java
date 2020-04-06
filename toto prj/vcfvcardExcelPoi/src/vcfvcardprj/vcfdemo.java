package vcfvcardprj;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.parameter.TelephoneType;

public class vcfdemo {
	
	
	public static void main(String[] args) throws IOException {
		  VCard vcard = new VCard(); 
          vcard.setFormattedName("aaaÃû×Ö");
          vcard.addTelephoneNumber("1888888", TelephoneType.CELL);
 //         vcard.addNickname( new Nickname().set csvReader.get(1));
       //   Organization organization = new Organization();organization.setType("jobeeÖ°Î»");
     //     organization.set
			vcard.setOrganization("comx-jobeex");
       
        String str = Ezvcard.write(vcard).version(VCardVersion.V3_0).go();
        FileUtils.write(new File("vcardout.vcf.txt"), str, "utf8");
        System.out.println("--");
	}

}
