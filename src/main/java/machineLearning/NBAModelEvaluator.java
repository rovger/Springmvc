package machineLearning;

import org.dmg.pmml.PMML;
import org.jpmml.evaluator.Evaluator;
import org.jpmml.evaluator.ModelEvaluatorFactory;
import org.jpmml.model.PMMLUtil;

/**
 * Created by weijlu on 2017/11/24.
 */
public class NBAModelEvaluator {
    private Evaluator evaluator;
    //private PMML pmml;
    /*static {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            pmml = PMMLUtil.unmarshal(loader.getResourceAsStream("pmml/training_result_nba.pmml"));
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }*/

    public NBAModelEvaluator() {
        try {
//            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            ClassLoader loader = this.getClass().getClassLoader();
            PMML pmml = PMMLUtil.unmarshal(loader.getResourceAsStream("pmml/training_result_nba.pmml"));
            evaluator = ModelEvaluatorFactory.newInstance().newModelEvaluator(pmml);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public Evaluator getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(Evaluator evaluator) {
        this.evaluator = evaluator;
    }
}
