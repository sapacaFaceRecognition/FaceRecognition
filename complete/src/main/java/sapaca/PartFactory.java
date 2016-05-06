package sapaca;

/**
 * Created by caro on 05.05.2016.
 */
public class PartFactory {

    public Part load(PartToDetect partToDetect) {
        if (partToDetect.equals(PartToDetect.FACE)) {
            PartFace partFace = new PartFace();
            partFace.setXmlFile();
            return partFace;
        }

        if (partToDetect.equals(PartToDetect.EYES)) {
            return new PartEyes();
        }

        if (partToDetect.equals(PartToDetect.PERSON)) {
            return new PartPerson();
        }

        return null;
    }
}
