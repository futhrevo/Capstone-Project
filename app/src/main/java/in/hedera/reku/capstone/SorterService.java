package in.hedera.reku.capstone;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.aliasi.chunk.Chunk;
import com.aliasi.chunk.Chunking;
import com.aliasi.dict.DictionaryEntry;
import com.aliasi.dict.ExactDictionaryChunker;
import com.aliasi.dict.MapDictionary;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class SorterService extends IntentService {

    static final double CHUNK_SCORE = 1.0;

//    private final static String PCG_MODEL = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
//
//    private final TokenizerFactory<CoreLabel> tokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(), "invertible=true");
//
//    private final LexicalizedParser parser = LexicalizedParser.loadModel(PCG_MODEL);


//    public Tree parse(String str) {
//        List<CoreLabel> tokens = tokenize(str);
//        Tree tree = parser.apply(tokens);
//        return tree;
//    }
//
//    private List<CoreLabel> tokenize(String str) {
//        Tokenizer<CoreLabel> tokenizer =
//                tokenizerFactory.getTokenizer(
//                        new StringReader(str));
//        return tokenizer.tokenize();
//    }

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "in.hedera.reku.capstone.action.FOO";
    private static final String ACTION_BAZ = "in.hedera.reku.capstone.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "in.hedera.reku.capstone.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "in.hedera.reku.capstone.extra.PARAM2";

    public SorterService() {
        super("SorterService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, SorterService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, SorterService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }else{
                testNLP();
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void testNLP(){

        String str = "One time Password (OTP) for your Internet banking is  VTP8EL  . If you are not accessing Net banking please contact RBL Branch Immediately.";

        MapDictionary<String> dictionary = new MapDictionary<String>();
        dictionary.addEntry(new DictionaryEntry<String>("One Time Password","OTP",CHUNK_SCORE));
        dictionary.addEntry(new DictionaryEntry<String>("verification code", "OTP", CHUNK_SCORE));

        ExactDictionaryChunker dictionaryChunkerTT
                = new ExactDictionaryChunker(dictionary,
                IndoEuropeanTokenizerFactory.INSTANCE,
                true,true);

        ExactDictionaryChunker dictionaryChunkerTF
                = new ExactDictionaryChunker(dictionary,
                IndoEuropeanTokenizerFactory.INSTANCE,
                true,false);

        ExactDictionaryChunker dictionaryChunkerFT
                = new ExactDictionaryChunker(dictionary,
                IndoEuropeanTokenizerFactory.INSTANCE,
                false,true);

        ExactDictionaryChunker dictionaryChunkerFF
                = new ExactDictionaryChunker(dictionary,
                IndoEuropeanTokenizerFactory.INSTANCE,
                false,false);



        System.out.println("\nDICTIONARY\n" + dictionary);

        chunk(dictionaryChunkerTT,str);
        chunk(dictionaryChunkerTF,str);
        chunk(dictionaryChunkerFT,str);
        chunk(dictionaryChunkerFF,str);

//        TreebankLanguagePack tlp = new PennTreebankLanguagePack();
//        GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
//        Tree parse = parser.parse(str);
//        GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
//        Collection<TypedDependency> tdl = gs.typedDependenciesCCprocessed();
//        System.out.println(tdl);
//        System.out.println(parse.pennString());


////        Parser parser = new Parser();
//        Tree tree = parse(str);
//
//        List<Tree> leaves = tree.getLeaves();
//        // Print words and Pos Tags
//        for (Tree leaf : leaves) {
//            Tree parent = leaf.parent(tree);
//            System.out.print(leaf.label().value() + "-" + parent.label().value() + " ");
//        }
//        System.out.println();
    }

    static void chunk(ExactDictionaryChunker chunker, String text) {
        System.out.println("\nChunker."
                + " All matches=" + chunker.returnAllMatches()
                + " Case sensitive=" + chunker.caseSensitive());
        Chunking chunking = chunker.chunk(text);
        for (Chunk chunk : chunking.chunkSet()) {
            int start = chunk.start();
            int end = chunk.end();
            String type = chunk.type();
            double score = chunk.score();
            String phrase = text.substring(start,end);
            System.out.println("     phrase=|" + phrase + "|"
                    + " start=" + start
                    + " end=" + end
                    + " type=" + type
                    + " score=" + score);
        }
    }
}
