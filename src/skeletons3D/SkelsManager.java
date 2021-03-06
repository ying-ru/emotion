package skeletons3D;

// SkelsManager.java
// Andrew Davison, October 2011, ad@fivedots.psu.ac.th

/* Manages the 3D skeletons in the scene.

 SkelsManager sets up 7 'observers' (listeners) so that 
 when a new user is detected in the scene, a standard pose for that 
 user is detected, the user skeleton is calibrated in the pose, and then the
 skeleton is tracked. 

 The start of tracking adds a 3D skeleton entry to userSkels3D, and attaches
 its scene graph to the 3D scene.

 The skeleton can be made invisible when the user exits, and visible
 again when they return to the Kinect's FOV. If the user is lost then the
 skeleton is deleted from the scene graoh and from userSkels3D.

 Each call to update() updates the 3D skeleton for each user
 via its Skeleton3D object
 */

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Observable;

import org.OpenNI.*;

import javax.media.j3d.*;

public class SkelsManager extends Observable implements Observer {
	// OpenNI
	private UserGenerator userGen;

	// capabilities used by UserGenerator
	private SkeletonCapability skelCap;
	// to output skeletal data, including the location of the joints
	private PoseDetectionCapability poseDetectionCap;
	// to recognize when the user is in a specific position

	private String calibPose = null;

	// Java3D
	private HashMap<Integer, Skeleton3D> userSkels3D;
	// maps user IDs --> a 3D skeleton

	private BranchGroup sceneBG; // the scene graph

	private FileWriter fw;
	private int time;
	private String writeTemp, jointTemp, isTracking;
	private Thread updateTime;
	private Vertex head, neck, torso, leftShoulder, rightShoulder, leftHip,
			rightHip, leftElbow, rightElbow;
	private File readFile;

	protected boolean canSave;

	public SkelsManager(UserGenerator userGen, BranchGroup sceneBG)
			throws IOException {
		this.userGen = userGen;
		this.sceneBG = sceneBG;

		configure();

		userSkels3D = new HashMap<Integer, Skeleton3D>();
		
		readFile = new File();
		head = new Vertex();
		neck = new Vertex();
		torso = new Vertex();
		leftShoulder = new Vertex();
		rightShoulder = new Vertex();
		leftHip = new Vertex();
		rightHip = new Vertex();
		leftElbow = new Vertex();
		rightElbow = new Vertex();
		
		time = 0;
		isTracking = "";
		writeTemp = null;
		jointTemp = null;
		write();
	} // end of SkelsManager()

	private void configure()
	/*
	 * create pose and skeleton detection capabilities for the user generator,
	 * and set up observers (listeners)
	 */
	{
		try {
			// setup UserGenerator pose and skeleton detection capabilities;
			// should really check these using
			// ProductionNode.isCapabilitySupported()
			poseDetectionCap = userGen.getPoseDetectionCapability();

			skelCap = userGen.getSkeletonCapability();
			calibPose = skelCap.getSkeletonCalibrationPose(); // the 'psi' pose
			skelCap.setSkeletonProfile(SkeletonProfile.ALL);
			// other possible values: UPPER_BODY, LOWER_BODY, HEAD_HANDS

			// set up 7 observers
			userGen.getNewUserEvent().addObserver(
					new NewUserObserver()); // new user found
			userGen.getLostUserEvent().addObserver(
					new LostUserObserver()); // lost a user
			userGen.getUserExitEvent().addObserver(
					new ExitUserObserver()); // user has exited (but may re-enter)
			userGen.getUserReenterEvent().addObserver(
					new ReEnterUserObserver()); // user has re-entered

			poseDetectionCap.getPoseDetectedEvent().addObserver(
					new PoseDetectedObserver()); // for when a pose is detected

			skelCap.getCalibrationStartEvent().addObserver(
					new CalibrationStartObserver()); // calibration is starting
			skelCap.getCalibrationCompleteEvent().addObserver(
					new CalibrationCompleteObserver());
			// for when skeleton calibration is completed, and tracking starts
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}
	} // end of configure()

	public void update() {
		// update skeleton of each user being tracked
		try {
			int[] userIDs = userGen.getUsers(); // there may be many users in
												// the scene
			for (int i = 0; i < userIDs.length; i++) {
				int userID = userIDs[i];
				if (skelCap.isSkeletonCalibrating(userID)) {
					continue; // test to avoid occassional crashes with
								// isSkeletonTracking()
				}
				if (skelCap.isSkeletonTracking(userID)) {
					userSkels3D.get(userID).update();
					writeTemp = userID + userSkels3D.get(userID).toString()
							+ "\n";
					notifyTrack();
				} else {
					writeTemp = null;
				}
			}
		} catch (StatusException e) {
			System.out.println(e);
		}
	} // end of update()

	private void updateVertex() {
		String[] joint = writeTemp.split(",");
		SimpleDateFormat fmt = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		String timeStamp = fmt.format(new Date());
		jointTemp = "'" + timeStamp + "'";
		for (int i = 1; i < joint.length; i++) {
			if (joint[i].equals("HEAD")) {
				head.setX(Math.round((Double.parseDouble(joint[i + 1])) * 100) / 100.0);
				head.setY(Math.round((Double.parseDouble(joint[i + 2])) * 100) / 100.0);
				head.setZ(Math.round((Double.parseDouble(joint[i + 3])) * 100) / 100.0);
			} else if (joint[i].equals("NECK")) {
				neck.setX(Math.round((Double.parseDouble(joint[i + 1])) * 100) / 100.0);
				neck.setY(Math.round((Double.parseDouble(joint[i + 2])) * 100) / 100.0);
				neck.setZ(Math.round((Double.parseDouble(joint[i + 3])) * 100) / 100.0);
			} else if (joint[i].equals("TORSO")) {
				torso.setX(Math.round((Double.parseDouble(joint[i + 1])) * 100) / 100.0);
				torso.setY(Math.round((Double.parseDouble(joint[i + 2])) * 100) / 100.0);
				torso.setZ(Math.round((Double.parseDouble(joint[i + 3])) * 100) / 100.0);
			} else if (joint[i].equals("LEFT_SHOULDER")) {
				leftShoulder.setX(Math.round((Double.parseDouble(joint[i + 1])) * 100) / 100.0);
				leftShoulder.setY(Math.round((Double.parseDouble(joint[i + 2])) * 100) / 100.0);
				leftShoulder.setZ(Math.round((Double.parseDouble(joint[i + 3])) * 100) / 100.0);
			} else if (joint[i].equals("LEFT_ELBOW")) {
				leftElbow.setX(Math.round((Double.parseDouble(joint[i + 1])) * 100) / 100.0);
				leftElbow.setY(Math.round((Double.parseDouble(joint[i + 2])) * 100) / 100.0);
				leftElbow.setZ(Math.round((Double.parseDouble(joint[i + 3])) * 100) / 100.0);
			} else if (joint[i].equals("RIGHT_SHOULDER")) {
				rightShoulder.setX(Math.round((Double.parseDouble(joint[i + 1])) * 100) / 100.0);
				rightShoulder.setY(Math.round((Double.parseDouble(joint[i + 2])) * 100) / 100.0);
				rightShoulder.setZ(Math.round((Double.parseDouble(joint[i + 3])) * 100) / 100.0);
			} else if (joint[i].equals("RIGHT_ELBOW")) {
				rightElbow.setX(Math.round((Double.parseDouble(joint[i + 1])) * 100) / 100.0);
				rightElbow.setY(Math.round((Double.parseDouble(joint[i + 2])) * 100) / 100.0);
				rightElbow.setZ(Math.round((Double.parseDouble(joint[i + 3])) * 100) / 100.0);
			} else if (joint[i].equals("LEFT_HIP")) {
				leftHip.setX(Math.round((Double.parseDouble(joint[i + 1])) * 100) / 100.0);
				leftHip.setY(Math.round((Double.parseDouble(joint[i + 2])) * 100) / 100.0);
				leftHip.setZ(Math.round((Double.parseDouble(joint[i + 3])) * 100) / 100.0);
			} else if (joint[i].equals("RIGHT_HIP")) {
				rightHip.setX(Math.round((Double.parseDouble(joint[i + 1])) * 100) / 100.0);
				rightHip.setY(Math.round((Double.parseDouble(joint[i + 2])) * 100) / 100.0);
				rightHip.setZ(Math.round((Double.parseDouble(joint[i + 3])) * 100) / 100.0);
			}
		}
//		jointTemp = jointTemp + ",raiseHead,"
//				+ raiseHead(head, torso, leftShoulder, rightShoulder);
//		jointTemp = jointTemp + ",bodyStraighten,"
//				+ bodyStraighten(torso, neck, leftHip, rightHip);
//		jointTemp = jointTemp + ",leftArms,"
//				+ leftArms(leftElbow, torso, leftShoulder, rightShoulder);
//		jointTemp = jointTemp + ",rightArms,"
//				+ rightArms(rightElbow, torso, leftShoulder, rightShoulder)
//				+ "\n";
		jointTemp = jointTemp + ","
				+ raiseHead(head, torso, leftShoulder, rightShoulder);
		jointTemp = jointTemp + ","
				+ bodyStraighten(torso, neck, leftHip, rightHip);
		jointTemp = jointTemp + ","
				+ leftArms(leftElbow, torso, leftShoulder, rightShoulder);
		jointTemp = jointTemp + ","
				+ rightArms(rightElbow, torso, leftShoulder, rightShoulder)
				+ "\n";
	}

	private double calculateCos(Vertex v1, Vertex v2, Vertex v3) {
		double cos = 0;
		double xOfv12, yOfv12, zOfv12, xOfv13, yOfv13, zOfv13;
		xOfv12 = v2.getX() - v1.getX();
		yOfv12 = v2.getY() - v1.getY();
		zOfv12 = v2.getZ() - v1.getZ();
		xOfv13 = v3.getX() - v1.getX();
		yOfv13 = v3.getY() - v1.getY();
		zOfv13 = v3.getZ() - v1.getZ();
		cos = xOfv12 * xOfv13 + yOfv12 * yOfv13 + zOfv12 * zOfv13;
		cos = cos / (Math.sqrt(Math.pow(xOfv12, 2) + Math.pow(yOfv12, 2)
					+ Math.pow(zOfv12, 2)) * Math.sqrt(Math.pow(xOfv13, 2)
					+ Math.pow(yOfv13, 2) + Math.pow(zOfv13, 2)));
		return Math.abs(cos);
	}

	private double calculateDistance(Vertex p, Vertex v1, Vertex v2, Vertex v3, double e) {
		double dis = 0;
		double a, b, c, d, cos;
		cos = calculateCos(v1, v2, v3);
		if (cos > e) {
			return -9999;
		} else {
			a = v1.y * (v2.z - v3.z) + v2.y * (v3.z - v1.z) + v3.y
					* (v1.z - v2.z);
			b = v1.z * (v2.x - v3.x) + v2.z * (v3.x - v1.x) + v3.z
					* (v1.x - v2.x);
			c = v1.x * (v2.y - v3.y) + v2.x * (v3.y - v1.y) + v3.x
					* (v1.y - v2.y);
			d = -(v1.x * (v2.y * v3.z - v3.y * v2.z) + v2.x
					* (v3.y * v1.z - v1.y * v3.z) + v3.x
					* (v1.y * v2.z - v2.y * v1.z));
			dis = (Math.round((a * p.x + b * p.y + c * p.z + d) * 1000.0) / 1000.0)
					/ (Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2)
							+ Math.pow(c, 2)));
			return dis;
		}
	}

	// 計算Head和Torso, Left_Shoulder, Right_Shoulder構成的平面的距離(深度)
	public double raiseHead(Vertex head, Vertex torso, Vertex leftShoulder,
			Vertex rightShoulder) { // 抬頭：+20 ~ -20
		double d;
		d = calculateDistance(head, torso, leftShoulder, rightShoulder, 0.9);
		if (d < -20) {
			return 1;
		} else if (d == -9999) {
			return -9999;
		} else if (d > 20) {
			return -1;
		} else {
			return Math.round(-(d / 20) * 10000) / 10000.0;
		}
	}

	// 計算Torso和Neck, Left_Hip, Right_Hip構成的平面的距離(深度)
	public double bodyStraighten(Vertex torso, Vertex neck, Vertex leftHip,
			Vertex rightHip) { // 身體直立：0.5 ~ -0.5
		double d;
		d = calculateDistance(torso, neck, leftHip, rightHip, 0.95);
		if (d > 0.005) {
			if (Math.round(d * 2 * 10000.0) / 100.0 > 0.5) {
				return 1;
			}
			return 1;
		} else if (d == -9999) {
			return -9999;
		} else if (d < -0.005) {
			if (Math.round(d * 2 * 10000.0) / 100.0 < -0.5) {
				return -1;
			}
			return -1;
		} else if ((d < 0.005 || d > -0.005) && (Math.round(d * 2 * 10000.0) / 100.0 < 0.5 
				||Math.round(d * 2 * 10000.0) / 100.0 > -0.5)) {
			System.out.println(d);
			
			return Math.round(d * 2 * 1000000.0) / 10000.0;
		} else {
			return -9999;
		}
	}

	// 分別計算Left_Elbow和 Right_Elbow對於Torso, Left_Shoulder,
	// Right_Shoulder構成的平面的距離(深度)
	public double leftArms(Vertex leftElbow, Vertex torso, Vertex leftShoulder,
			Vertex rightShoulder) { // 臂不向前：250 ~ 0
		double d;
		d = calculateDistance(leftElbow, torso, leftShoulder, rightShoulder, 0.9);
		if (d < 0) {
			return 1;
		} else if (d == -9999) {
			return -9999;
		} else if (d > 250) {
			return -1;
		} else {
			return Math.round((d * 2 / 250 - 1) * 10000) / 10000.0;
		}
	}

	public double rightArms(Vertex rightElbow, Vertex torso,
			Vertex leftShoulder, Vertex rightShoulder) {
		double d;
		d = calculateDistance(rightElbow, torso, leftShoulder, rightShoulder, 0.9);
		if (d < 0) {
			return 1;
		} else if (d == -9999) {
			return -9999;
		} else if (d > 250) {
			return -1;
		} else {
			return Math.round((d * 2 / 250 - 1) * 10000) / 10000.0;
		}
	}
	
	public void setWrite() {
    	canSave = true;
    }
    
    public void write() {
		Thread update = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					int i = 0;
					while (true) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						if (canSave && i < 10 && writeTemp != null) {
							if (i == 0) {
								fw = new FileWriter("src/file/kinect.csv");
							}
							System.out.println("OK");
							updateVertex();
							fw.append(jointTemp);
							System.out.println(writeTemp);
							i++;
						} else if (i >= 10) {
							fw.flush();
							fw.close();
							i = 0;
							canSave = false;
							isTracking = "kok";
							setChanged();
							notifyObservers(isTracking);
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		update.start();
	}

	// ----------------- 7 observers -----------------------
	/*
	 * user detection --> pose detection --> skeleton calibration starts -->
	 * skeleton calibration finish --> skeleton tracking (causes the creation of
	 * userSkels3D entry + scene graph)
	 * 
	 * + exit --> re-entry of user (3D skeleton is made invisible/visible)
	 * 
	 * + lose a user (causes the deletion of its userSkels3D entry + scene
	 * graph)
	 */

	class NewUserObserver implements IObserver<UserEventArgs> {
		public void update(IObservable<UserEventArgs> observable,
				UserEventArgs args) {
			System.out.println("Detected new user " + args.getId());
			try {
				// try to detect a pose for the new user
				poseDetectionCap.StartPoseDetection(calibPose, args.getId());
			} catch (StatusException e) {
				e.printStackTrace();
			}
		}
	} // end of NewUserObserver inner class

	class LostUserObserver implements IObserver<UserEventArgs> {
		public void update(IObservable<UserEventArgs> observable,
				UserEventArgs args) {
			int userID = args.getId();
			System.out.println("Lost track of user " + userID);

			// delete skeleton from userSkels3D and the scene graph
			Skeleton3D skel = userSkels3D.remove(userID);
			if (skel == null)
				return;
			skel.delete();
		}
	} // end of LostUserObserver inner class

	class ExitUserObserver implements IObserver<UserEventArgs> {
		public void update(IObservable<UserEventArgs> observable,
				UserEventArgs args) {
			int userID = args.getId();
			System.out.println("Exit of user " + userID);

			// make 3D skeleton invisible when user exits
			Skeleton3D skel = userSkels3D.get(userID);
			if (skel == null)
				return;
			skel.setVisibility(false);
		}
	} // end of ExitUserObserver inner class

	class ReEnterUserObserver implements IObserver<UserEventArgs> {
		public void update(IObservable<UserEventArgs> observable,
				UserEventArgs args) {
			int userID = args.getId();
			System.out.println("Reentry of user " + userID);

			// make 3D skeleton visible when user re-enters
			Skeleton3D skel = userSkels3D.get(userID);
			if (skel == null)
				return;
			skel.setVisibility(true);
		}
	} // end of ReEnterUserObserver inner class

	class PoseDetectedObserver implements IObserver<PoseDetectionEventArgs> {
		public void update(IObservable<PoseDetectionEventArgs> observable,
				PoseDetectionEventArgs args) {
			int userID = args.getUser();
			System.out.println(args.getPose() + " pose detected for user "
					+ userID);
			try {
				// finished pose detection; switch to skeleton calibration
				poseDetectionCap.StopPoseDetection(userID);
				skelCap.requestSkeletonCalibration(userID, true);
			} catch (StatusException e) {
				e.printStackTrace();
			}
		}
	} // end of PoseDetectedObserver inner class

	class CalibrationStartObserver implements
			IObserver<CalibrationStartEventArgs> {
		public void update(IObservable<CalibrationStartEventArgs> observable,
				CalibrationStartEventArgs args) {
			System.out
					.println("Calibration started for user " + args.getUser());
		}
	} // end of CalibrationStartObserver inner class

	class CalibrationCompleteObserver implements
			IObserver<CalibrationProgressEventArgs> {
		public void update(
				IObservable<CalibrationProgressEventArgs> observable,
				CalibrationProgressEventArgs args) {
			int userID = args.getUser();
			System.out.println("Calibration status: " + args.getStatus()
					+ " for user " + userID);
			try {
				if (args.getStatus() == CalibrationProgressStatus.OK) {
					// calibration succeeeded; move to skeleton tracking
					System.out.println("Starting tracking user " + userID);
					skelCap.startTracking(userID);

					// create skeleton3D in userSkels3D, and add to scene
					Skeleton3D skel = new Skeleton3D(userID, skelCap);
					userSkels3D.put(userID, skel);
					sceneBG.addChild(skel.getBG());
				} else
					// calibration failed; return to pose detection
					poseDetectionCap.StartPoseDetection(calibPose, userID);
			} catch (StatusException e) {
				e.printStackTrace();
			}
		}
	} // end of CalibrationCompleteObserver inner class
	
	public void notifyTrack() {
		if (!isTracking.equals("kinect") && !isTracking.equals("kok")) {
			isTracking = "kinect";
			setChanged();
			notifyObservers(isTracking);
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
} // end of SkelsManager class

