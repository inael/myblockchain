import com.google.gson.GsonBuilder;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Application {



    public static void main(String[] args) throws InterruptedException {


        Scanner sc = new Scanner(System.in);

        System.out.println(TextConstants.ENTER_THE_NUMBER_OF_THREADS);
        Integer threadNumber = new Integer(sc.next());


        System.out.println(TextConstants.ENTER_THE_NUMBER_OF_BLOCKS_PER_MINER);
        Integer blockNumber = new Integer(sc.next());

        System.out.println(TextConstants.ENTER_A_NUMBER_TO_REPRESENT_THE_MINING_DIFFICULTY_EG_1_1000);
        Integer difficultyMineBlock = new Integer(sc.next());

        ExecutorService executorService = Executors.newFixedThreadPool(threadNumber);
        BlockChain blockChain = new BlockChain(difficultyMineBlock);
        blockChain.addGenesisBlock();



        long start = System.currentTimeMillis();

        IntStream.range(0, threadNumber).forEach(i -> {
            Miner miner = new Miner(i, blockChain, blockNumber);
            executorService.submit(miner);
        });


        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockChain.getBlockList());
        System.out.println(String.format(TextConstants.THE_BLOCK_CHAIN,blockchainJson));

        long finish = System.currentTimeMillis();
        System.out.println(String.format(TextConstants.TIME_ELAPSED_TO_END_MINERS,finish - start));
        System.out.println(String.format(TextConstants.BLOCKCHAIN_IS_VALID, SecurityUtil.isChainValid(blockChain)));
    }
}
