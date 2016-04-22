package sapaca;
import static org.bytedeco.javacpp.opencv_core.Mat;
import static org.bytedeco.javacpp.opencv_core.MatVector;
import static org.bytedeco.javacpp.opencv_highgui.imread;
import static org.bytedeco.javacpp.opencv_highgui.imwrite;
import static org.bytedeco.javacpp.opencv_stitching.Stitcher;



public class GenderClassification {
	
	 static boolean try_use_gpu = false;
	    static MatVector imgs = new MatVector();
	    static String result_name = "result.jpg";

	    public static void main(String[] args) {
	        int retval = parseCmdArgs(args);
	        if (retval != 0) {
	            System.exit(-1);
	        }

	        Mat pano = new Mat();
	        Stitcher stitcher = Stitcher.createDefault(try_use_gpu);
	        int status = stitcher.stitch(imgs, pano);

	        if (status != Stitcher.OK) {
	            System.out.println("Can't stitch images, error code = " + status);
	            System.exit(-1);
	        }

	        imwrite(result_name, pano);

	        System.out.println("Images stitched together to make " + result_name);
	    }

	    static void printUsage() {
	        System.out.println(
	           "stitching img1 img2 [...imgN]\n\n"
	          + "Flags:\n"
	          + "  --try_use_gpu (yes|no)\n"
	          + "  --output <result_img>\n"
	          + "      The default is 'result.jpg'.");
	    }

	    static int parseCmdArgs(String[] args) {
	        if (args.length == 0) {
	            printUsage();
	            return -1;
	        }
	        for (int i = 0; i < args.length; i++) {
	            if (args[i].equals("--help") || args.equals("/?")) {
	                printUsage();
	                return -1;
	            } else if (args[i].equals("--try_use_gpu")) {
	                if (args[i + 1].equals("no")) {
	                    try_use_gpu = false;
	                } else if (args[i + 1].equals("yes")) {
	                    try_use_gpu = true;
	                } else {
	                    System.out.println("Bad --try_use_gpu flag value");
	                    return -1;
	                }
	                i++;
	            } else if (args[i].equals("--output")) {
	                result_name = args[i + 1];
	                i++;
	            } else {
	                Mat img = imread(args[i]);
	                if (img.empty()) {
	                    System.out.println("Can't read image '" + args[i] + "'");
	                    return -1;
	                }
	                imgs.resize(imgs.size() + 1);
	                imgs.put(imgs.size() - 1, img);
	            }
	        }
	        return 0;
	    }
	
}
