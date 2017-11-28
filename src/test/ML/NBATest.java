package ML;

import machineLearning.NBA;
import machineLearning.NBAModelEvaluator;
import org.dmg.pmml.FieldName;
import org.jpmml.evaluator.Evaluator;
import org.jpmml.evaluator.InputField;
import org.jpmml.evaluator.ProbabilityDistribution;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by weijlu on 2017/11/24.
 */
public class NBATest {

    static final String BLS = "BLS";
    static final String ASS = "ASS";
    static final String PTS = "PTS";
    static final String STS = "STS";
    static final String REBS = "REBS";

    @Test
    public void testMachinLearning() {

        NBA nba = new NBA(12,15,3,2,5);

        NBAModelEvaluator model = new NBAModelEvaluator();
        Evaluator evaluator = model.getEvaluator();
        List<InputField> inputFields = evaluator.getActiveFields();
        Map<FieldName, Object> map = new HashMap<>();
        /*[InputField{name=BLS, dataType=FLOAT, opType=CONTINUOUS}, InputField{name=ASS, dataType=FLOAT, opType=CONTINUOUS},
        InputField{name=PTS, dataType=FLOAT, opType=CONTINUOUS}, InputField{name=STS, dataType=FLOAT, opType=CONTINUOUS},
        InputField{name=REBS, dataType=FLOAT, opType=CONTINUOUS}]*/
        //System.out.println(inputFields.toString());
        for (InputField inputField : inputFields) {
            FieldName fieldName = inputField.getName();
            String fName = fieldName.getValue();
            if (BLS.equals(fName)) {
                map.put(fieldName, nba.getBls());
            }
            if (ASS.equals(fName)) {
                map.put(fieldName, nba.getAss());
            }
            if (PTS.equals(fName)) {
                map.put(fieldName, nba.getPts());
            }
            if (STS.equals(fName)) {
                map.put(fieldName, nba.getSts());
            }
            if (REBS.equals(fName)) {
                map.put(fieldName, nba.getRebs());
            }
        }
        Map<FieldName, ProbabilityDistribution> results = (Map<FieldName, ProbabilityDistribution>) evaluator.evaluate(map);
        FieldName targetFieldName = evaluator.getTargetFields().get(0).getName();
        Double prediction = (Double) results.get(targetFieldName).getResult();
        System.out.println("prediction:" + prediction.intValue());
    }

}
