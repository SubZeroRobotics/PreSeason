package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;


@Autonomous(name= "EOCVdetector", group="Sky autonomous")
public class EOCVdetector extends LinearOpMode {


        private ElapsedTime runtime = new ElapsedTime();
        /*
        //0 means skystone, 1 means yellow stone
        //-1 for debug, but we can keep it like this because if it works, it should change to either 0 or 255
        private static int valMid = -1;
        private static int valLeft = -1;
        private static int valRight = -1;

        private static float rectHeight = .6f/8f;
        private static float rectWidth = 1.5f/8f;

        private static float offsetX = 0f/8f;//changing this moves the three rects and the three circles left or right, range : (-2, 2) not inclusive
        private static float offsetY = 0f/8f;//changing this moves the three rects and circles up or down, range: (-4, 4) not inclusive

        private static float[] midPos = {4f/8f+offsetX, 4f/8f+offsetY};//0 = col, 1 = row
        private static float[] leftPos = {2f/8f+offsetX, 4f/8f+offsetY};
        private static float[] rightPos = {6f/8f+offsetX, 4f/8f+offsetY};
        //moves all rectangles right or left by amount. units are in ratio to monitor


         */

        public int leftX;
        public int leftY;
        public int rightX;
        public int rightY;

       private static double[] globalLeftPix;
       private static double[] globalRightPix;
       private static int globalLeftColor;
       private static int  globalRightColor;


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
        Mat thresholdMat = new Mat();
        Mat grayMat = new Mat();
        Mat all = new Mat();



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
            Imgproc.cvtColor(input, yCbCrChan2Mat, Imgproc.COLOR_RGB2YCrCb); //converts rgb to ycrcb                           cvtColor(InputArray src, OutputArray dst, int code, int dstCn=0 )
            Imgproc.cvtColor(input,grayMat, Imgproc.COLOR_RGB2GRAY);
            Imgproc.threshold(yCbCrChan2Mat, thresholdMat, 102, 255, Imgproc.THRESH_BINARY_INV); //bw threshhold threshold(InputArray src, OutputArray dst, double thresh, double maxval, int type)
            //create a point
            Point leftPoint = new Point(120,200);
            Point rightPoint = new Point(360,200);
            Point textPoint = new Point(240,20);
            //create circle
            Imgproc.circle(grayMat, leftPoint, 60, new Scalar(0,255,0),4 ); //void circle(Mat img, Point center, int radius, const Scalar& color, int thickness=1, int lineType=8, int shift=0)
            Imgproc.circle(grayMat, rightPoint, 60, new Scalar(0,255,0),4 );

            //create a dot
            Imgproc.circle(grayMat, leftPoint, 5, new Scalar(0,255,0),4);
            Imgproc.circle(grayMat, rightPoint, 5, new Scalar(0,255,0),4);
            //get the colors of the points
            globalLeftPix = grayMat.get(120,200);
            globalRightPix = grayMat.get(360,200);

            globalLeftColor = (int)globalLeftPix[0];
            globalRightColor = (int)globalRightPix[0];






            return grayMat;
        }

    }




    }

