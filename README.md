Here’s the updated README with placeholders for your image links in the samples section:

---

# EVAN - Emergency Vehicle Assistance Network

EVAN is an innovative mobile application designed to provide seamless roadside assistance to drivers encountering emergencies. Whether it's a flat tire, dead battery, or running out of fuel, EVAN ensures help is just a few taps away. Developed as a final-year project by a team of Computer Science students at Yarmouk University, EVAN combines user-centric design with cutting-edge technology to deliver reliable support for drivers worldwide.

EVAN is a dual-application platform designed to provide seamless roadside assistance services through:
1. **User App**: Empowers drivers to request immediate help during vehicle emergencies.
2. **Admin App**: Facilitates the management of assistance requests and provider coordination.

These applications utilize advanced technologies like Firebase, Google Maps, and real-time geolocation to ensure quick and efficient service delivery.

---

## About the Applications

### Domain:
- **Roadside Assistance**: Connecting drivers with assistance providers in emergencies.
- **Purpose**: Improve driver safety and reduce stress during vehicle breakdowns.

### User App Features:
- **Request Assistance**: Services include towing, fuel delivery, battery jump-starts, flat tire repair, and unlocking car doors.
- **Real-Time Tracking**: View the provider's location and ETA.
- **Cost Estimation**: Transparent pricing based on service type and distance.
- **Profile Management**: Update user details and contact support.

### Admin App Features:
- **Request Management**: View, accept, and resolve user requests.
- **Location Navigation**: Direct navigation to user locations using Google Maps.
- **Request Completion**: Mark requests as completed upon service delivery.
- **Admin Dashboard**: Real-time display of all active requests.

---

## Project Objectives

1. **Enhance User Convenience**:
   - Provide an easy-to-use platform for requesting emergency roadside services.
2. **Efficient Request Management**:
   - Streamline the process for service providers to locate and assist users.
3. **Real-Time Updates**:
   - Leverage geolocation to offer accurate ETAs and navigation.

---

## Project Presentation
### Key Highlights:
- Roadside assistance made accessible and efficient through dual applications.
- Firebase integration for secure and real-time communication.
- Geolocation services for real-time tracking and provider navigation.
[Evan presantation](<https://github.com/radwanzoubi1/EVAN/blob/main/App%20description/evan%20Presentation%20last%20edition.pptx>)
---

## System Diagrams

### Use Case Diagram:
The Use Case Diagram illustrates the interaction between users, providers, and admins in the EVAN system.

![Use Case Diagram](<https://github.com/radwanzoubi1/EVAN/blob/main/App%20description/Use%20Case%20diagram.JPG>)

---

### Data Flow Diagram:
A Data Flow Diagram (DFD) represents the flow of information between the components of EVAN.

![Data Flow Diagram](<https://github.com/radwanzoubi1/EVAN/blob/main/App%20description/Data%20flow%20diagram.JPG>)

---

### Flowchart:
This flowchart explains the sequence of processes from user request submission to service completion.

![Flowchart](<https://github.com/radwanzoubi1/EVAN/blob/main/App%20description/Flow%20chart.JPG>)

---

## App Samples

### **User App:**
1. **Login and Registration**:
   - Secure login for existing users.
   - Simple registration for new users.
   ![Login and Registration](<https://github.com/radwanzoubi1/EVAN/blob/main/App%20description/Login.JPG>)

2. **Profile and Home**:
   - View and update user information.
   - Select services directly from the home screen.
   ![Profile and Home](<https://github.com/radwanzoubi1/EVAN/blob/main/App%20description/Profile%20and%20home.JPG>)

3. **Submit Order and Waiting for Provider**:
   - Submit requests with geolocation and service type.
   - Track provider's location and ETA.
   ![Submit Order and Waiting](<https://github.com/radwanzoubi1/EVAN/blob/main/App%20description/Submit%20the%20oreder.JPG>)

---

### **Admin App:**
1. **Admin Dashboard**:
   - View and manage all user requests in real time.
   - Navigate to user locations using integrated Google Maps.
   ![Admin Dashboard](https://github.com/radwanzoubi1/EVAN/blob/main/App%20description/admin%20app.JPG)

---

## Technologies Used

- **Programming Language**: Java.
- **Framework**: Android Studio.
- **Database**: Firebase Firestore for real-time data storage.
- **Geolocation Services**: Google Maps API, Android Location Services.
- **Design**: Material Design for intuitive interfaces.

---

## How to Run

### Prerequisites:
1. **Android Studio** installed on your machine.
2. **Firebase Project**:
   - Configure Firebase for both apps.
   - Add `google-services.json` to the `app/` directory.
3. **Google Maps API Key**:
   - Set up an API key for location services.

### Steps:
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/radwanzoubi1/EVAN.git
   ```
2. **Import Projects**:
   - Open `evan_user` and `evan_admin` in Android Studio.
3. **Build and Run**:
   - Connect your device or emulator and build the apps.

---

## Project Structure

### User App:
```plaintext
evan_user/
├── java/com.example.e_van/
│   ├── Splash_screen.java
│   ├── Login_Page.java
│   ├── Home_Page.java
│   ├── Submit_Page.java
│   ├── Waiting.java
│   ├── Profile_Page.java
│   ├── HelperClass.java
│   ├── DistanceCalculator.java
├── res/
│   ├── layout/  # XML files for UI
│   ├── values/  # Strings, colors, and styles
```

### Admin App:
```plaintext
evan_admin/
├── java/com.example.e_van_admin/
│   ├── SplashScreen.java
│   ├── MainActivity.java
│   ├── HomeAdmin.java
│   ├── CardAdapter.java
│   ├── CardItem.java
│   ├── DistanceCalculator.java
├── res/
│   ├── layout/  # XML files for UI
│   ├── values/  # Strings, colors, and styles
```

---

## Results

### User App:
- Successfully facilitates quick and transparent roadside assistance requests.
- Real-time tracking and feedback integration enhance user satisfaction.

### Admin App:
- Efficiently manages and fulfills user requests.
- Google Maps navigation streamlines service delivery.

---

## Tools and Libraries Used

1. **Firebase**:
   - Authentication for secure logins.
   - Firestore for real-time data storage.
2. **Google Maps API**:
   - For navigation and distance calculations.
3. **Java and Android SDK**:
   - Core development environment.
4. **Material Design**:
   - For a clean and modern user interface.
