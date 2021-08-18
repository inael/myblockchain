import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class BlockChain {

    /**
     * It will be necessary to increase the amount of elements very often, so the ideal is to use the Vector that
     * increases twice and will have much more space than the Synchronized ArrayList (Collections.synchronizedList
     * (new ArrayList<>())) that will need to be increasing more often,
     * thus decreasing the application's performance.
     */

    private Vector<Block> blockList = new Vector<>();
    Map<String, Integer> reportSumOfBlocksPerMiner = new HashMap<String, Integer>();


    private int difficultyMineBlock;

    public BlockChain(int difficultyMineBlock) {
        this.difficultyMineBlock = difficultyMineBlock;
    }

    public synchronized void addBlock(Block newBlock, Miner miner) {

        Block lastBlock = blockList.get(blockList.size() - 1);

        System.out.println(String.format(TextConstants.MINER_TRYING_ADD_NEW_BLOCK, miner.getName(),
                newBlock.getData()));

        if (lastBlock.getHash().equals(newBlock.getPreviousHash()) && newBlock.getHash().equals(newBlock.calculateHash())) {
            System.out.println(String.format(TextConstants.MINER_ADDING_NEW_BLOCK_S, miner.getName(),
                    newBlock.getData()));

            blockList.add(newBlock);
            updateReport(miner);

            System.out.println(String.format(TextConstants.MINER_ADICIONOU_O_BLOCK, miner.getName(),
                    newBlock.getData()));
        } else {
            System.out.println(String.format(TextConstants.BLOCK_IS_INVALID, miner.getName(),
                    newBlock.getData()));
        }
        System.out.println(String.format("Is Valid BlockChain %s", isChainValid()));

    }

    private void updateReport(Miner miner) {
        Integer blockSum = new Integer(0);
        if(reportSumOfBlocksPerMiner.containsKey(miner.getName())) {
             blockSum = this.reportSumOfBlocksPerMiner.get(miner.getName());
        }
        this.reportSumOfBlocksPerMiner.put(miner.getName(), new Integer(blockSum + 1));
    }

    public void printReportSumOfBlocksPerMiner() {
        System.out.println("---------------Report Sum Of Blocks Per Miner----------------------");
        this.reportSumOfBlocksPerMiner.entrySet().forEach(entry ->
                System.out.println(String
                        .format(TextConstants.SUM_OF_BLOCKS_PER_MINER, entry.getKey(), entry.getValue()))
        );
        System.out.println("-------------------------------------");
    }

    public synchronized Vector<Block> getBlockList() {
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
            Block currentBlock;
            Block previousBlock;
            String hashTarget = new String(new char[difficultyMineBlock]).replace('\0', '0');

            //loop through blockchain to check hashes:
            for (int i = 1; i < blockList.size(); i++) {
                currentBlock = blockList.get(i);
                previousBlock = blockList.get(i - 1);
                //compare registered hash and calculated hash:
                if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                    System.out.println("Current Hashes not equal");
                    return false;
                }
                //compare previous hash and registered previous hash
                if (!previousBlock.getHash().equals(currentBlock.getPreviousHash())) {
                    System.out.println("Previous Hashes not equal");
                    return false;
                }
                //check if hash is solved
                if (!currentBlock.getHash().substring(0, difficultyMineBlock).equals(hashTarget)) {
                    System.out.println("This block hasn't been mined");
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
}
