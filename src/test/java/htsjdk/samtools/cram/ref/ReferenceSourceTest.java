package htsjdk.samtools.cram.ref;

import htsjdk.samtools.SAMSequenceRecord;
import htsjdk.samtools.reference.InMemoryReferenceSequenceFile;
import htsjdk.samtools.util.SequenceUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by vadim on 29/06/2017.
 */
public class ReferenceSourceTest {

    @Test
    public void testReferenceSourceUpperCasesBases() throws NoSuchAlgorithmException, IOException {
        final String sequenceName = "1";
        final String nonIupacCharacters = "1=eE";
        final byte[] originalRefBases = (nonIupacCharacters+SequenceUtil.IUPAC_CODES_STRING).getBytes();
        SAMSequenceRecord sequenceRecord = new SAMSequenceRecord(sequenceName, originalRefBases.length);

        InMemoryReferenceSequenceFile memoryReferenceSequenceFile = new InMemoryReferenceSequenceFile();
        memoryReferenceSequenceFile.add(sequenceName, Arrays.copyOf(originalRefBases, originalRefBases.length));
        Assert.assertEquals(memoryReferenceSequenceFile.getSequence(sequenceName).getBases(), originalRefBases);

        ReferenceSource referenceSource = new ReferenceSource(memoryReferenceSequenceFile);
        byte[] refBasesFromSource = referenceSource.getReferenceBases(sequenceRecord, false);

        Assert.assertNotEquals(refBasesFromSource, originalRefBases);
        Assert.assertEquals(refBasesFromSource, SequenceUtil.upperCase(originalRefBases));
    }
}
