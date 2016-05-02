package sapaca;
import static org.bytedeco.javacpp.opencv_core.Mat;
import static org.bytedeco.javacpp.opencv_core.MatVector;
import static org.bytedeco.javacpp.opencv_highgui.imread;
import static org.bytedeco.javacpp.opencv_highgui.imwrite;
import static org.bytedeco.javacpp.opencv_stitching.Stitcher;



public class GenderClassification {

    private GenderClassification() {
    }

	 private static boolean tryUseGpu = false;
	    private static MatVector imgs = new MatVector();
	    private static String resultName = "result.jpg";

	    public static void main(String[] args) {
	        int retval = parseCmdArgs(args);
	        if (retval != 0) {
	            System.exit(-1);
	        }

	        Mat pano = new Mat();
	        Stitcher stitcher = Stitcher.createDefault(tryUseGpu);
	        int status = stitcher.stitch(imgs, pano);

	        if (status != Stitcher.OK) {
                System.console().writer().println("Can't stitch images, error code = " + status);
	            System.exit(-1);
	        }

	        imwrite(resultName, pano);
            System.console().writer().println("Images stitched together to make " + resultName);
	    }

	    private static void printUsage() {
            System.console().writer().println(
	           "stitching img1 img2 [...imgN]\n\n"
	          + "Flags:\n"
	          + "  --try_use_gpu (yes|no)\n"
	          + "  --output <result_img>\n"
	          + "      The default is 'result.jpg'.");
	    }

	    private static int parseCmdArgs(String[] args) {
	        if (args.length == 0) {
	            printUsage();
	            return -1;
	        }
	        for (int i = 0; i < args.length; i++) {
	            if ("--help".equals(args[i])) {
	                printUsage();
	                return -1;
	            } else if ("--tryUseGpu".equals(args[i])) {
	                if ("no".equals(args[i + 1])) {
                        tryUseGpu = false;
	                } else if ("yes".equals(args[i + 1])){
                        tryUseGpu = true;
	                } else {
                        System.console().writer().println("Bad --tryUseGpu flag value");
	                    return -1;
	                }
	                i++;
	            } else if ("--output".equals(args[i])) {
	                resultName = args[i + 1];
	                i++;
	            } else {
	                Mat img = imread(args[i]);
	                if (img.empty()) {
                        System.console().writer().println("Can't read image '" + args[i] + "'");
	                    return -1;
	                }
	                imgs.resize(imgs.size() + 1);
	                imgs.put(imgs.size() - 1, img);
	            }
	        }
	        return 0;
	    }

}
