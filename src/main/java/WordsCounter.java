/**
 * Created by namlai on 2017/07/08.
 */

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class WordsCounter {

    static Map<String, Boolean> stopWords = new HashMap<>();
    static Map<String, Boolean> punctuationWords = new HashMap<>();
    static Map<String, Boolean> unnecessaryPOSTags = new HashMap<>();

    static {
        stopWords.put("a", true);
        stopWords.put("about", true);
        stopWords.put("above", true);
        stopWords.put("after", true);
        stopWords.put("again", true);
        stopWords.put("against", true);
        stopWords.put("all", true);
        stopWords.put("am", true);
        stopWords.put("an", true);
        stopWords.put("and", true);
        stopWords.put("any", true);
        stopWords.put("are", true);
        stopWords.put("aren't", true);
        stopWords.put("as", true);
        stopWords.put("at", true);
        stopWords.put("be", true);
        stopWords.put("because", true);
        stopWords.put("been", true);
        stopWords.put("before", true);
        stopWords.put("being", true);
        stopWords.put("below", true);
        stopWords.put("between", true);
        stopWords.put("both", true);
        stopWords.put("but", true);
        stopWords.put("by", true);
        stopWords.put("can't", true);
        stopWords.put("cannot", true);
        stopWords.put("could", true);
        stopWords.put("couldn't", true);
        stopWords.put("did", true);
        stopWords.put("didn't", true);
        stopWords.put("do", true);
        stopWords.put("does", true);
        stopWords.put("doesn't", true);
        stopWords.put("doing", true);
        stopWords.put("don't", true);
        stopWords.put("down", true);
        stopWords.put("during", true);
        stopWords.put("each", true);
        stopWords.put("few", true);
        stopWords.put("for", true);
        stopWords.put("from", true);
        stopWords.put("further", true);
        stopWords.put("had", true);
        stopWords.put("hadn't", true);
        stopWords.put("has", true);
        stopWords.put("hasn't", true);
        stopWords.put("have", true);
        stopWords.put("haven't", true);
        stopWords.put("having", true);
        stopWords.put("he", true);
        stopWords.put("he'd", true);
        stopWords.put("he'll", true);
        stopWords.put("he's", true);
        stopWords.put("her", true);
        stopWords.put("here", true);
        stopWords.put("here's", true);
        stopWords.put("hers", true);
        stopWords.put("herself", true);
        stopWords.put("him", true);
        stopWords.put("himself", true);
        stopWords.put("his", true);
        stopWords.put("how", true);
        stopWords.put("how's", true);
        stopWords.put("i", true);
        stopWords.put("i'd", true);
        stopWords.put("i'll", true);
        stopWords.put("i'm", true);
        stopWords.put("i've", true);
        stopWords.put("if", true);
        stopWords.put("in", true);
        stopWords.put("into", true);
        stopWords.put("is", true);
        stopWords.put("isn't", true);
        stopWords.put("it", true);
        stopWords.put("it's", true);
        stopWords.put("its", true);
        stopWords.put("itself", true);
        stopWords.put("let's", true);
        stopWords.put("me", true);
        stopWords.put("more", true);
        stopWords.put("most", true);
        stopWords.put("mustn't", true);
        stopWords.put("my", true);
        stopWords.put("myself", true);
        stopWords.put("no", true);
        stopWords.put("nor", true);
        stopWords.put("not", true);
        stopWords.put("of", true);
        stopWords.put("off", true);
        stopWords.put("on", true);
        stopWords.put("once", true);
        stopWords.put("only", true);
        stopWords.put("or", true);
        stopWords.put("other", true);
        stopWords.put("ought", true);
        stopWords.put("our", true);
        stopWords.put("ours", true);
        stopWords.put("", true);
        stopWords.put("about", true);
        stopWords.put("above", true);
        stopWords.put("after", true);
        stopWords.put("again", true);
        stopWords.put("against", true);
        stopWords.put("all", true);
        stopWords.put("am", true);
        stopWords.put("an", true);
        stopWords.put("and", true);
        stopWords.put("any", true);
        stopWords.put("are", true);
        stopWords.put("aren't", true);
        stopWords.put("as", true);
        stopWords.put("at", true);
        stopWords.put("be", true);
        stopWords.put("because", true);
        stopWords.put("been", true);
        stopWords.put("before", true);
        stopWords.put("being", true);
        stopWords.put("below", true);
        stopWords.put("between", true);
        stopWords.put("both", true);
        stopWords.put("but", true);
        stopWords.put("by", true);
        stopWords.put("can't", true);
        stopWords.put("cannot", true);
        stopWords.put("could", true);
        stopWords.put("couldn't", true);
        stopWords.put("did", true);
        stopWords.put("didn't", true);
        stopWords.put("do", true);
        stopWords.put("does", true);
        stopWords.put("doesn't", true);
        stopWords.put("doing", true);
        stopWords.put("don't", true);
        stopWords.put("down", true);
        stopWords.put("during", true);
        stopWords.put("each", true);
        stopWords.put("few", true);
        stopWords.put("for", true);
        stopWords.put("from", true);
        stopWords.put("further", true);
        stopWords.put("had", true);
        stopWords.put("hadn't", true);
        stopWords.put("has", true);
        stopWords.put("hasn't", true);
        stopWords.put("have", true);
        stopWords.put("haven't", true);
        stopWords.put("having", true);
        stopWords.put("he", true);
        stopWords.put("he'd", true);
        stopWords.put("he'll", true);
        stopWords.put("he's", true);
        stopWords.put("her", true);
        stopWords.put("here", true);
        stopWords.put("here's", true);
        stopWords.put("hers", true);
        stopWords.put("herself", true);
        stopWords.put("him", true);
        stopWords.put("himself", true);
        stopWords.put("his", true);
        stopWords.put("how", true);
        stopWords.put("how's", true);
        stopWords.put("i", true);
        stopWords.put("i'd", true);
        stopWords.put("i'll", true);
        stopWords.put("i'm", true);
        stopWords.put("i've", true);
        stopWords.put("if", true);
        stopWords.put("in", true);
        stopWords.put("into", true);
        stopWords.put("is", true);
        stopWords.put("isn't", true);
        stopWords.put("it", true);
        stopWords.put("it's", true);
        stopWords.put("its", true);
        stopWords.put("itself", true);
        stopWords.put("let's", true);
        stopWords.put("me", true);
        stopWords.put("more", true);
        stopWords.put("most", true);
        stopWords.put("mustn't", true);
        stopWords.put("my", true);
        stopWords.put("myself", true);
        stopWords.put("no", true);
        stopWords.put("nor", true);
        stopWords.put("not", true);
        stopWords.put("of", true);
        stopWords.put("off", true);
        stopWords.put("on", true);
        stopWords.put("once", true);
        stopWords.put("only", true);
        stopWords.put("or", true);
        stopWords.put("other", true);
        stopWords.put("ought", true);
        stopWords.put("our", true);
        stopWords.put("ours	ourselves", true);
        stopWords.put("out", true);
        stopWords.put("over", true);
        stopWords.put("own", true);
        stopWords.put("same", true);
        stopWords.put("shan't", true);
        stopWords.put("she", true);
        stopWords.put("she'd", true);
        stopWords.put("she'll", true);
        stopWords.put("she's", true);
        stopWords.put("should", true);
        stopWords.put("shouldn't", true);
        stopWords.put("so", true);
        stopWords.put("some", true);
        stopWords.put("such", true);
        stopWords.put("than", true);
        stopWords.put("that", true);
        stopWords.put("that's", true);
        stopWords.put("the", true);
        stopWords.put("their", true);
        stopWords.put("theirs", true);
        stopWords.put("them", true);
        stopWords.put("themselves", true);
        stopWords.put("then", true);
        stopWords.put("there", true);
        stopWords.put("there's", true);
        stopWords.put("these", true);
        stopWords.put("they", true);
        stopWords.put("they'd", true);
        stopWords.put("they'll", true);
        stopWords.put("they're", true);
        stopWords.put("they've", true);
        stopWords.put("this", true);
        stopWords.put("those", true);
        stopWords.put("through", true);
        stopWords.put("to", true);
        stopWords.put("too", true);
        stopWords.put("under", true);
        stopWords.put("until", true);
        stopWords.put("up", true);
        stopWords.put("very", true);
        stopWords.put("was", true);
        stopWords.put("wasn't", true);
        stopWords.put("we", true);
        stopWords.put("we'd", true);
        stopWords.put("we'll", true);
        stopWords.put("we're", true);
        stopWords.put("we've", true);
        stopWords.put("were", true);
        stopWords.put("weren't", true);
        stopWords.put("what", true);
        stopWords.put("what's", true);
        stopWords.put("when", true);
        stopWords.put("when's", true);
        stopWords.put("where", true);
        stopWords.put("where's", true);
        stopWords.put("which", true);
        stopWords.put("while", true);
        stopWords.put("who", true);
        stopWords.put("who's", true);
        stopWords.put("whom", true);
        stopWords.put("why", true);
        stopWords.put("why's", true);
        stopWords.put("with", true);
        stopWords.put("won't", true);
        stopWords.put("would", true);
        stopWords.put("wouldn't", true);
        stopWords.put("you", true);
        stopWords.put("you'd", true);
        stopWords.put("you'll", true);
        stopWords.put("you're", true);
        stopWords.put("you've", true);
        stopWords.put("your", true);
        stopWords.put("yours", true);
        stopWords.put("yourself", true);
        stopWords.put("yourselves", true);


        // Additional punctuation charsets
        stopWords.put(":", true);
        stopWords.put(".", true);
        stopWords.put(",", true);
        stopWords.put("--", true);
        stopWords.put("'s", true);
        stopWords.put("'s", true);
    }

    static {
        // Additional punctuation charsets
        punctuationWords.put(":", true);
        punctuationWords.put(".", true);
        punctuationWords.put(",", true);
        punctuationWords.put("--", true);
        punctuationWords.put("'s", true);
        punctuationWords.put("?", true);
        punctuationWords.put("``", true);
        punctuationWords.put("''", true);
        punctuationWords.put("n't", true);
        punctuationWords.put(";", true);
        punctuationWords.put("'ve", true);
        punctuationWords.put("'re", true);
        punctuationWords.put("'m", true);
    }

    static {
//        unnecessaryPOSTags.put("CC", false);   //Coordinating conjunction
        unnecessaryPOSTags.put("CD", true);   //Cardinal number
//        unnecessaryPOSTags.put("DT", false);   //Determiner
//        unnecessaryPOSTags.put("EX", false);   //Existential there
//        unnecessaryPOSTags.put("FW", false);   //Foreign word
//        unnecessaryPOSTags.put("IN", false);   //Preposition or subordinating conjunction
//        unnecessaryPOSTags.put("JJ", false);   //Adjective
//        unnecessaryPOSTags.put("JJR", false);    //Adjective, comparative
//        unnecessaryPOSTags.put("JJS", false);    //Adjective, superlative
//        unnecessaryPOSTags.put("LS", false);   //List item marker
//        unnecessaryPOSTags.put("MD", false);   //Modal
//        unnecessaryPOSTags.put("NN", false);   //Noun, singular or mass
//        unnecessaryPOSTags.put("NNS", false);    //Noun, plural
//        unnecessaryPOSTags.put("NNP", false);    //Proper noun, singular
//        unnecessaryPOSTags.put("NNPS", false);   //Proper noun, plural
//        unnecessaryPOSTags.put("PDT", false);    //Predeterminer
//        unnecessaryPOSTags.put("POS", false);    //Possessive ending
//        unnecessaryPOSTags.put("PRP", false);    //Personal pronoun
//        unnecessaryPOSTags.put("PRP$", false);   //	Possessive pronoun
//        unnecessaryPOSTags.put("RB", false);   //Adverb
//        unnecessaryPOSTags.put("RBR", false);    //Adverb, comparative
//        unnecessaryPOSTags.put("RBS", false);    //Adverb, superlative
//        unnecessaryPOSTags.put("RP", false);   //Particle
//        unnecessaryPOSTags.put("SYM", false);    //Symbol
//        unnecessaryPOSTags.put("TO", false);   //to
//        unnecessaryPOSTags.put("UH", false);   //Interjection
//        unnecessaryPOSTags.put("VB", false);   //Verb, base form
//        unnecessaryPOSTags.put("VBD", false);    //Verb, past tense
//        unnecessaryPOSTags.put("VBG", false);    //Verb, gerund or present participle
//        unnecessaryPOSTags.put("VBN", false);    //Verb, past participle
//        unnecessaryPOSTags.put("VBP", false);    //Verb, non-3rd person singular present
//        unnecessaryPOSTags.put("VBZ", false);    //Verb, 3rd person singular present
//        unnecessaryPOSTags.put("WDT", false);    //Wh-determiner
//        unnecessaryPOSTags.put("WP", false);   //Wh-pronoun
//        unnecessaryPOSTags.put("WP$", false);    //Possessive wh-pronoun
//        unnecessaryPOSTags.put("WRB", false);    //Wh-adverb
    }


    public static void main(String[] args) throws IOException {
//        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
//        for (CoreMap sentence : sentences) {
//            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
//                String word = token.get(CoreAnnotations.TextAnnotation.class);
//                // this is the POS tag of the token
//                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
//                System.out.println(word + "/" + pos);
//            }
//        }


        String inputFilePath = "src/main/resources/dataSet/env/input_1.txt";
        String inputDirPath = "src/main/resources/dataSet/env";
        String outputFilePath = "src/main/resources/output.txt";

        Properties props = new Properties();

        props.setProperty("annotators", "tokenize, ssplit, pos");

        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);


        File[] files = new File(inputDirPath).listFiles();
        StringBuffer inputStringBuffer = new StringBuffer();
        for (File file1 : files) {
            if (!file1.isFile()) {
                System.out.println("Warning: directory path is storing directory child.");
                return;
            }

            String filePath = file1.getPath();
            Paths.get(filePath);
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
            inputStringBuffer.append(new String(bytes).toLowerCase());

        }


        String content = inputStringBuffer.toString();


        // TODO: stemming here
//        CharArraySet stopWords = EnglishAnalyzer.getDefaultStopSet();
//        TokenStream tokenStream = new StandardTokenizer(new StringReader(content));
//        StopFilter stopFilter = new StopFilter(tokenStream, stopWords);
//
//        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
//        tokenStream.reset();
//        while (stopFilter.incrementToken()) {
//            String s = charTermAttribute.toString();
//            System.out.println(s);
//        }


        Annotation annotation = new Annotation(content);

        pipeline.annotate(annotation);

        List<CoreLabel> list = new ArrayList<>();

        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            list.addAll(sentence.get(CoreAnnotations.TokensAnnotation.class));
        }


        Map<String, Long> wordCount = list
                .stream()
                .map(coreLabel -> new AbstractMap.SimpleEntry<>(coreLabel.get(CoreAnnotations.TextAnnotation.class), 1))
                .collect(groupingBy(AbstractMap.SimpleEntry::getKey, counting()));

        List<WordFrequency> wf = new ArrayList<>();


        LinkedHashMap<String, Long> collect = wordCount.entrySet()
                .stream()
                .filter(removeStopWord())
                .filter(removePunctuation())
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));


        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Long> entry : collect.entrySet()) {
            stringBuilder.append(entry.getKey());
            stringBuilder.append(":");
            stringBuilder.append(entry.getValue());
            stringBuilder.append("\n");
        }


        FileUtils.writeStringToFile(new File(outputFilePath), stringBuilder.toString());

    }

    public static Predicate<Map.Entry<String, Long>> removeStopWord() {
        return p -> !stopWords.containsKey(p.getKey());
    }

    public static Predicate<Map.Entry<String, Long>> removePunctuation() {
        return p -> !punctuationWords.containsKey(p.getKey());
    }
}
