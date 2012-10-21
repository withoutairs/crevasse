final vaultName = "family_media";
final endpoint = "https://glacier.us-east-1.amazonaws.com/"
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.glacier.transfer.ArchiveTransferManager
import com.amazonaws.services.glacier.transfer.UploadResult
import org.slf4j.*

final dryrun = true
ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("AGC");
logger.setLevel(ch.qos.logback.classic.Level.INFO);
logger.debug("debug")
logger.info("info")

AWSCredentials credentials = new PropertiesCredentials(
        AmazonGlacierClient.class.getResourceAsStream("AwsCredentials.properties"))
client = new com.amazonaws.services.glacier.AmazonGlacierClient(credentials)
client.setEndpoint(endpoint)
ArchiveTransferManager atm = new ArchiveTransferManager(client, credentials)

dir = new File("/Users/cbrown/Pictures")
if (!(dir.isDirectory())) {
    println "${dir.canonicalPath} is not a directory."
    System.exit(1)
}

dir.traverse { archiveToUpload ->
    logger.debug("Processing ${archiveToUpload}")
    final description = "my archive " + (new Date())
    if (!dryrun) {
        UploadResult result = atm.upload(vaultName, description, new File(archiveToUpload))
        print "Archive ID: ${result.getArchiveId()}"
    }
}
