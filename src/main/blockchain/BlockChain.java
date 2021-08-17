import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlockChain {

    private static List<Block> blockchain = Collections.synchronizedList(new ArrayList<Block>());
    private static int difficultyMineBlock;

    public BlockChain(int difficultyMineBlock) {
        this.difficultyMineBlock = difficultyMineBlock;
    }

    public synchronized void addBlock(Block newBlock, Miner miner) {
        System.out.println(String.format(TextConstants.MINER_ADDING_NEW_BLOCK_S, miner.getName(), newBlock.getData()));
        blockchain.add(newBlock);
        System.out.println(String.format(TextConstants.MINER_ADICIONOU_O_BLOCK, miner.getName(), newBlock.getData()));
        System.out.println(String.format("Is Valid BlockChain %s",isChainValid()));
    }

    public String getPreviousBlockHash() {
        return blockchain.get(blockchain.size() - 1).getHash();
    }

    public List<Block> getBlockList() {
        return blockchain;
    }

    public void addGenesisBlock() {
        blockchain.add(new Block("\"Starts from here!\"","0"));
    }

    public int getDifficultyMineBlock() {
        return this.difficultyMineBlock;
    }



    public static  Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficultyMineBlock]).replace('\0', '0');

        //loop through blockchain to check hashes:
        for(int i=1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);
            //compare registered hash and calculated hash:
            if(!currentBlock.getHash().equals(currentBlock.calculateHash()) ){
                System.out.println("Current Hashes not equal");
                return false;
            }
            //compare previous hash and registered previous hash
            if(!previousBlock.getHash().equals(currentBlock.getPreviousHash()) ) {
                System.out.println("Previous Hashes not equal");
                return false;
            }
            //check if hash is solved
            if(!currentBlock.getHash().substring( 0, difficultyMineBlock).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }
        }
        return true;
    }
}
