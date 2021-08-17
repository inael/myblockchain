import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;

@Getter
@ToString
public class Block {
    private int previousHash;
    private String data;
    private int hash;

    public Block(String data, int previousHash) {
        this.data = data;
        this.previousHash = previousHash;

        // Mix the content of this block with previous hash to create the hash of this new block
        // and that's what makes it block chain
        this.hash  = Arrays.hashCode(new Integer[]{data.hashCode(), previousHash});
    }
}
