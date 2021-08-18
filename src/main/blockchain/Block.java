import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Block {
    private String previousHash;
    private String hash;
    private String data;
    private long timeStamp;
    private int nounce;

    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash  = calculateHash();
    }
    public String calculateHash() {
        String completeData  = new StringBuilder()
                .append(previousHash)
                .append(timeStamp)
                .append(nounce)
                .append(data).toString();
        return SecurityUtil.applySha256(completeData);
    }

    /**
     * Performing Proof-Of-Work
     * @param difficulty
     * @return
     */
    public String mineBlock(int difficulty) {

        String prefixString = new String(new char[difficulty]).replace('\0', '0');
        while (!hash.substring(0, difficulty).equals(prefixString)) {
            nounce++;
            hash = calculateHash();
        }
        return hash;
    }

    public String toString() {
        return ("Block #" + data + "\n\tmined at: " + this.timeStamp + "\n\tHash: {"
                + this.hash + "}\n");
    }


}
