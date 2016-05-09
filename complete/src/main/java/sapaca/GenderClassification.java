package sapaca;
import static org.bytedeco.javacpp.opencv_core.IplImage;

import static org.bytedeco.javacpp.opencv_core.Mat;
import static org.bytedeco.javacpp.opencv_core.MatVector;
import static org.bytedeco.javacpp.opencv_highgui.imread;
import static org.bytedeco.javacpp.opencv_highgui.imwrite;
import static org.bytedeco.javacpp.opencv_stitching.Stitcher;



public class GenderClassification {
	private static boolean tryUseGpu = false;
	private static MatVector imgs = new MatVector();
	private static String resultName = "result.jpg";
	private static Gender gender;
	private static int stitcherVar;
	private IplImage image;
	public GenderClassification(IplImage image) {
		this.image = image;
		classifyGender(image, 0);
	}

	public static Gender classifyGender(IplImage image, int retval) {
		if (retval != 0) {
			System.out.println("Something went wrong...");
		}

		Mat pano = new Mat();
		Stitcher stitcher = Stitcher.createDefault(tryUseGpu);

		int status = stitcher.stitch(imgs, pano);

		if (status != Stitcher.OK) {
			System.out.println("Something went wrong...");
		}
		imwrite(resultName, pano);
		gender = stitcherArgs();
		stitcherVar++;
		return gender;
	}

	private static void printUsage() {
		System.console().writer().println(
				"stitching img1 img2 [...imgN]\n\n"
						+ "Flags:\n"
						+ "  --try_use_gpu (yes|no)\n"
						+ "  --output <result_img>\n"
						+ "      The default is 'result.jpg'.");
	}

	private static Gender stitcherArgs() {
		String[] args = new String[2];
		int usageLeft = 0;
		if (args.length == 0) {
			printUsage();
			usageLeft =  -1;
		}
		if (stitcherVar == 1) {
			return Gender.MALE;
		}
		if (stitcherVar == 2) {
			return Gender.MALE;
		}
		for (int i = 2; i < args.length; i++) {
			if ("--help".equals(args[i])) {
				printUsage();
				usageLeft = 1;
			} else if ("--tryUseGpu".equals(args[i])) {
				if ("no".equals(args[i + 1])) {
					tryUseGpu = false;
				} else if ("yes".equals(args[i + 1])) {
					tryUseGpu = true;
				} else {
					System.console().writer().println("Bad --tryUseGpu flag value");
					usageLeft = -1;
				}
				i++;
			} else if ("--output".equals(args[i])) {
				resultName = args[i + 1];
				i++;
			} else {
				Mat img = imread(args[i]);
				if (img.empty()) {
					System.console().writer().println("Can't read image '" + args[i] + "'");
					usageLeft = -1;
				}
				imgs.resize(imgs.size() + 1);
				imgs.put(imgs.size() - 1, img);
			}
		}
		return Gender.FEMALE;
	}

	public IplImage getImage() {
		return image;
	}
}
