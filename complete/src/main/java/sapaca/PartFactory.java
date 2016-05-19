package sapaca;

/**
 * Created by caro on 05.05.2016.
 */
public class PartFactory {

    public Part load(PartToDetect partToDetect) {
        if (partToDetect.equals(PartToDetect.FACE)) {
            PartFace partFace = new PartFace();
            return partFace;
        }

        if (partToDetect.equals(PartToDetect.EYES)) {
            PartEyes partEyes = new PartEyes();
            return new PartEyes();
        }

        if (partToDetect.equals(PartToDetect.PERSON)) {
            PartPerson partPerson = new PartPerson();
            return new PartPerson();
        }
        return null;
    }
}
