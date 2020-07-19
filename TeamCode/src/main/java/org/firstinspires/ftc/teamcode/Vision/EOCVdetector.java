package org.firstinspires.ftc.teamcode.Vision;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Vision.opencvDetector;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;


@Autonomous(name= "EOCVdetector", group="Sky autonomous")
public class EOCVdetector extends LinearOpMode {


        private ElapsedTime runtime = new ElapsedTime();

        public int leftX;
        public int leftY;
        public int rightX;
        public int rightY;

       private static double[] globalLeftPix;
       private static double[] globalRightPix;
       private static int globalLeftColor;
       private static int  globalRightColor;

    public static int left_one = 80;
    public static int left_two = 200;
    public static int left_three = 110;
    public static int left_four = 210;

    public static int right_one = 160;
    public static int right_two = 200;
    public static int right_three = 190;
    public static int right_four = 210;


        private final int rows = 640;
        private final int cols = 480;


        OpenCvCamera phoneCam;

        @Override
        public void runOpMode() throws InterruptedException {

            int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId); //getting phone camera hw mapping
            phoneCam.openCameraDevice();//open camera
            phoneCam.setPipeline(new StageSwitchingPipeline());//different stages
            phoneCam.startStreaming(rows, cols, OpenCvCameraRotation.UPRIGHT);//display on RC
            //width, height
            //width = height in this case, because camera is in portrait mode.
            waitForStart();
            runtime.reset();
            while (opModeIsActive()) {



                telemetry.addData("Left Color: ", globalLeftColor);
                telemetry.addData("Right Color: ",globalRightColor );

                telemetry.update();
               sleep(100);


            }
        }


        //---------------------------------------------------------------------


    //detection pipeline
     static class StageSwitchingPipeline extends OpenCvPipeline {

        Mat yCbCrChan2Mat = new Mat();
        Mat grayMat = new Mat();
        Mat leftMineral = new Mat();
        Mat rightMineral = new Mat();

        int[] leftSquare = {left_one, left_two, left_three, left_four};
        int[] rightSquare = {right_one, right_two, right_three, right_four};




        double[] refPixLeft;
        double[] refPixRight;


        //List<MatOfPoint> contoursList = new ArrayList<>();

        enum Stage {//color difference. greyscale
            detection,//includes outlines
            THRESHOLD,//b&w
            RAW_IMAGE,//displays raw view
        }

        private opencvDetector.StageSwitchingPipeline.Stage stageToRenderToViewport = opencvDetector.StageSwitchingPipeline.Stage.detection;
        private opencvDetector.StageSwitchingPipeline.Stage[] stages = opencvDetector.StageSwitchingPipeline.Stage.values();

        @Override
        public void onViewportTapped() {
            /*
             * Note that this method is invoked from the UI thread
             * so whatever we do here, we must do quickly.
             */

            int currentStageNum = stageToRenderToViewport.ordinal();

            int nextStageNum = currentStageNum + 1;

            if (nextStageNum >= stages.length) {
                nextStageNum = 0;
            }

            stageToRenderToViewport = stages[nextStageNum];
        }

        @Override

        //process frame method that gets continuously called
        public Mat processFrame(Mat input) {
            //mat
            Imgproc.cvtColor(input, yCbCrChan2Mat, Imgproc.COLOR_RGB2YCrCb); //converts rgb to ycrcb
            //populate 2 arrays of square coordinates
            int[] leftSquare =
                    {left_one,
                    left_two, left_three,
                    left_four};
            int[] rightSquare =
                    {right_one,
                    right_two,
                    right_three,
                    right_four};

            //create two rectangles
            Imgproc.rectangle(input,
                    new Point(leftSquare[0], leftSquare[1]),
                    new Point(leftSquare[2], leftSquare[3]),
                    new Scalar(0,255,0),1 );

            Imgproc.rectangle(input,
                    new Point(rightSquare[0], rightSquare[1]),
                    new Point(rightSquare[2], rightSquare[3]),
                    new Scalar(0,255,0),1 );


            //create two regions for where the mineral might lie in those squares
            leftMineral = yCbCrChan2Mat.submat((leftSquare[1], leftSquare[3], leftSquare[0], leftSquare[2]);
            rightMineral = yCbCrChan2Mat.submat((rightSquare[1], rightSquare[3], rightSquare[0], rightSquare[2]);


            /*
            Point leftPoint = new Point(120,200);
            Point rightPoint = new Point(360,200);
            Point textPoint = new Point(240,20);
            //create circle
            Imgproc.circle(grayMat, leftPoint, 60, new Scalar(0,255,0),4 ); //void circle(Mat img, Point center, int radius, const Scalar& color, int thickness=1, int lineType=8, int shift=0)
            Imgproc.circle(grayMat, rightPoint, 60, new Scalar(0,255,0),4 );

            //create squares

            //create a dot
            Imgproc.circle(grayMat, leftPoint, 5, new Scalar(0,255,0),4);
            Imgproc.circle(grayMat, rightPoint, 5, new Scalar(0,255,0),4);
            //get the colors of the points
            globalLeftPix = grayMat.get(120,200);
            globalRightPix = grayMat.get(360,200);

            globalLeftColor = (int)globalLeftPix[0];
            globalRightColor = (int)globalRightPix[0];



             */




            return input;
        }

    }




    }

