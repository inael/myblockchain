import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Logger;
import java.util.stream.IntStream;


/**
 * It will be necessary to increase the amount of elements very often, so the ideal is to use the Vector that
 * increases twice and will have much more space than the Synchronized ArrayList (Collections.synchronizedList
 * (new ArrayList<>())) that will need to be increasing more often,
 * thus decreasing the application's performance.
 */
@Getter
public class BlockChain {
    private static Logger logger = Logger.getLogger(SecurityUtil.class.getName());
    private Vector<Block> blockList = new Vector<>();
    Map<String, Integer> reportSumOfBlocksPerMiner = new HashMap<String, Integer>();

    private int difficultyMineBlock;

    public BlockChain(int difficultyMineBlock) {
        this.difficultyMineBlock = difficultyMineBlock;
        this.addGenesisBlock();
    }

    public synchronized void addBlock(Block newBlock, Miner miner) {

        Block lastBlock = blockList.get(blockList.size() - 1);

        print(newBlock, miner, TextConstants.MINER_TRYING_ADD_NEW_BLOCK);

        if (isValidBlock(newBlock, lastBlock)) {
            print(newBlock, miner, TextConstants.MINER_ADDING_NEW_BLOCK_S);

            blockList.add(newBlock);

            updateReport(miner);

            print(newBlock, miner, TextConstants.MINER_ADICIONOU_O_BLOCK);
        } else {
            print(newBlock, miner, TextConstants.BLOCK_IS_INVALID);
        }
    }

    public void printReportSumOfBlocksPerMiner() {
       logger.info("---------------Report Sum Of Blocks Per Miner----------------------");
        this.reportSumOfBlocksPerMiner.entrySet().forEach(entry ->
               logger.info(String
                        .format(TextConstants.SUM_OF_BLOCKS_PER_MINER, entry.getKey(), entry.getValue()))
        );
       logger.info("-------------------------------------");
    }

    public  Vector<Block> getBlockList() {
        return blockList;
    }

    public void addGenesisBlock() {
        Block genesis = new Block("\"Starts from here!\"", "0");
        blockList.add(genesis);
    }

    public int getDifficultyMineBlock() {
        return this.difficultyMineBlock;
    }

    public Boolean isChainValid() {
        synchronized (this) {
            String hashTarget = new String(new char[difficultyMineBlock]).replace('\0', '0');

            //loop through blockchain to check hashes:
            for (int i = 1; i < blockList.size(); i++) {
               Block currentBlock = blockList.get(i);
                Block previousBlock = blockList.get(i - 1);
                //compare registered hash and calculated hash:
                if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                   logger.info("Current Hashes not equal");
                    return false;
                }
                //compare previous hash and registered previous hash
                if (!previousBlock.getHash().equals(currentBlock.getPreviousHash())) {
                   logger.info("Previous Hashes not equal");
                    return false;
                }
                //check if hash is solved
                if (!currentBlock.getHash().substring(0, difficultyMineBlock).equals(hashTarget)) {
                   logger.info("This block hasn't been mined");
                    return false;
                }
            }
            return true;
        }
    }

    public Block getLastBlock() {
        int lastBlockIndex = blockList.size() - 1;
        return blockList.get(lastBlockIndex);
    }

    private void print(Block newBlock, Miner miner, String minerAddingNewBlockS) {
        logger.info(String.format(minerAddingNewBlockS, miner.getName(),
                newBlock.getData()));
    }

    private boolean isValidBlock(Block newBlock, Block lastBlock) {
        return lastBlock.getHash().equals(newBlock.getPreviousHash()) && newBlock.getHash().equals(newBlock.calculateHash());
    }

    private void updateReport(Miner miner) {
        Integer blockSum = new Integer(0);
        if(reportSumOfBlocksPerMiner.containsKey(miner.getName())) {
            blockSum = this.reportSumOfBlocksPerMiner.get(miner.getName());
        }
        this.reportSumOfBlocksPerMiner.put(miner.getName(), new Integer(blockSum + 1));
    }
}
