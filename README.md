
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

## Dataset Overview

### User Requests Database:
- **Data Type**: Real-time request details.
- **Attributes**:
  - **User Information**: Name, phone, email.
  - **Service Details**: Request type, location, cost.
  - **Geolocation**: Latitude and longitude.

### Admin Management Database:
- **Data Type**: Firebase collections.
- **Attributes**:
  - **Requests**: Organized by user, service, and status.
  - **Provider Feedback**: Status updates, completion records.

---

## Key Features

### **User App**:
1. **Request Submission**:
   - Submit requests with geolocation and service type.
2. **Live Tracking**:
   - Monitor provider's progress and estimated arrival.
3. **Feedback Integration**:
   - Share feedback after service completion.

### **Admin App**:
1. **Dashboard**:
   - List active user requests with details.
2. **Real-Time Navigation**:
   - Integrated Google Maps navigation.
3. **Request Fulfillment**:
   - Complete requests and remove from active list.

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
   https://github.com/radwanzoubi1/EVAN.git
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

---

## Future Enhancements

1. **Cross-Platform Support**:
   - Develop iOS versions of both apps.
2. **Analytics Dashboard**:
   - Add detailed analytics for service performance.
3. **Advanced Features**:
   - Multi-language support.
   - AI-based predictions for ETAs and service recommendations.
