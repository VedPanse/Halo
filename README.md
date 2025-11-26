# **Halo — Disaster-Resilient Mesh Network & Survivor Localization System**

## **A Kotlin Multiplatform Project (KMP Contest 2026 Submission)**

**Halo** is a cross-platform disaster-response communication system that functions even when **cell towers, Wi-Fi, and internet connectivity fail**. Built using **Kotlin Multiplatform**, Halo transforms smartphones into **offline mesh nodes** capable of:

* routing short emergency messages
* broadcasting survivor presence
* estimating locations even without GPS
* forming an autonomous offline survivor heatmap
* supporting optional AR-based survivor detection

Halo is designed to serve as a **life-saving network of last resort** during earthquakes, hurricanes, building collapses, and blackout zones.

---

## **Table of Contents**

* [Introduction](#introduction)
* [Core Goals](#core-goals)
* [System Overview](#system-overview)
* [Halo Core (Guaranteed MVP)](#halo-core-guaranteed-mvp)
* [Halo AR Locator (Stretch Goal)](#halo-ar-locator-stretch-goal)
* [Technical Architecture](#technical-architecture)
* [Mesh Networking Protocol](#mesh-networking-protocol)
* [Location Estimation](#location-estimation)
* [Data & Storage](#data--storage)
* [Power Management](#power-management)
* [Platform Modules](#platform-modules)
* [Security Model](#security-model)
* [Demo Plan](#demo-plan)
* [Implementation Roadmap](#implementation-roadmap)
* [Future Extensions](#future-extensions)

---

# **Introduction**

In real disasters, the most dangerous resource to lose is **communication**.

* Cell networks collapse
* GPS becomes unreliable
* People are trapped under rubble
* First responders have no visibility

**Halo solves this by turning every smartphone into a signal beacon and mesh router.**
Survivors’ phones coordinate with others nearby, forming a resilient **peer-to-peer emergency network**.

Halo operates **fully offline**.
It requires no servers, no infrastructure, no SIM card, no cloud.

---

# **Core Goals**

## ✔ **Enable communication without infrastructure**

Phones relay emergency packets device-to-device.

## ✔ **Locate survivors without GPS**

Using IMU fusion, RSSI-based range estimation, and multi-hop inference.

## ✔ **Create a live local map of survivor clusters**

First responders see nearby devices, density, distance, and signal pathways.

## ✔ **Be cross-platform and production-grade**

Build a heavy logic engine shared across Android, iOS, and Desktop using Kotlin Multiplatform (KMP).

## ✔ **Make the AR module optional**

The system is fully complete without AR. AR only enhances visualization when time allows.

---

 # **System Overview**

Halo consists of two major components:

1. **Halo Core** — minimal, robust, contest-ready mesh + survivor mapping
2. **Halo AR Locator** — optional AR-view for rescuers using camera + sensor fusion

The architecture prioritizes:

* reliability
* offline resilience
* low power consumption
* lightweight, compressible packets
* real-time device awareness

---

# **Halo Core (Guaranteed MVP)**

The MVP is a functional, stable offline mesh system with the following features:

### **1. Mesh Networking Layer**

* BLE advertising + scanning
* WiFi Direct peer discovery
* Multi-hop routing using a simplified AODV/BATMAN hybrid
* Packet TTL, dedup, and compression
* Node heartbeat broadcasts

### **2. Local Position Estimation**

* GPS when available
* Dead-reckoning (accelerometer + gyroscope + step count)
* Basic Kalman fusion (shared KMP module)
* Distance estimation using BLE RSSI smoothing

### **3. Survivor Map**

A real-time offline map that shows:

* Nearby nodes
* Estimated positions
* Clusters of survivors
* Node metadata (battery, movement, timestamp)

### **4. Emergency Messaging**

* Short SOS messages
* Multi-hop propagation
* Status updates (“injured,” “safe,” “trapped,” etc.)

### **5. Ultra-Low Power Mode**

* Minimal beacon frequency
* Background-only operation
* CPU wake minimization
* Automatic battery preservation logic

### **6. Clean Minimal UI**

Built with Compose Multiplatform:

* Map
* Device list
* SOS panel
* Power mode toggle

This alone is more than enough for a top-tier submission.

---

# **Halo AR Locator (Stretch Goal)**

This module is built **only if time permits** — it is not needed for the MVP.

The AR Locator gives first responders a **camera-based augmented view** showing **triangulated survivor positions** as 3D AR markers.

### **Capabilities**

* Fullscreen AR view with ARKit/ARCore
* Survivor nodes anchored as glowing markers
* Distance/direction estimation using BLE + IMU
* Multi-hop triangulation when direct signal is blocked
* Optional acoustic fusion (if hardware allows)
* 3D heatmap mode for cluster visualization

### **Purpose**

This module elevates Halo from a mesh system into a **next-gen search-and-rescue tool**, but is intentionally treated as an optional enhancement.

---

# **Technical Architecture**
<img width="992" height="483" alt="image" src="https://github.com/user-attachments/assets/8868ee11-165a-4583-8eaa-b0f08ff7dd16" />

```
+----------------------------------------------------------+
|                       Shared KMP Core                    |
|  - Routing protocol                                      |
|  - Packet serialization/compression                      |
|  - Location + IMU fusion (Kalman)                        |
|  - Survivor clustering                                   |
|  - Map data store (SQLDelight)                           |
+-------------------------+-------------------------------+
          Android Module                iOS Module
    - BLE scan/adv              |   - CoreBluetooth
    - WiFi Direct               |   - Multipeer/nearby modules
    - Foreground services       |   - Background modes (limited)
    - ARCore (stretch)          |   - ARKit (stretch)
```

---

# **Mesh Networking Protocol**

Halo will implement a lightweight hybrid:

* **AODV-style route discovery** (on demand)
* **BATMAN-style probabilistic rebroadcasting**
* **TTL + duplicate suppression**
* **Adaptive rebroadcast delay for congestion control**

Packet types:

* `NODE_BEACON`
* `POSITION_UPDATE`
* `SOS_MESSAGE`
* `ROUTE_REQUEST`
* `ROUTE_REPLY`

Serialization:
Compact binary with:

* varints
* optional gzip or simple custom bit-packing
* < 150 bytes per packet

---

# **Location Estimation**

Halo uses a hybrid pipeline:

### **GPS Mode**

When satellites available → real GPS.

### **Dead-Reckoning Mode**

When GPS is lost:

* Step detection
* IMU angle integration
* Drift correction from mesh neighbors
* Kalman filtered fused state

### **Triangulation Assistance**

Even without GPS:

* RSSI distance smoothing
* Multi-hop inferred position
* Simple trilateration when >2 neighbors exist

---

# **Data & Storage**

Using **SQLDelight** for shared storage:

* Node table
* Position history
* Packet log (bounded LRU)
* Offline map tile cache

All storage is compact and safe in low-memory conditions.

---

# **Power Management**

Disasters mean dying batteries.
Halo includes:

* Adaptive beacon frequency
* Duty-cycled BLE scanning
* Foreground-service fallback
* “Emergency Mode” that forces ultra-low usage
* Packet batching when possible

Goal:
**Survive 24–48 hours on low battery.**

---

# **Platform Modules**

### **Android**

* BLE advertiser/scanner
* WiFi Direct
* Foreground service
* ARCore (optional)
* Sensors: accelerometer, gyro, barometer

### **iOS**

* CoreBluetooth
* Multipeer connectivity (optional fallback)
* Limited background modes
* ARKit (optional)
* Sensors: accelerometer, gyro, barometer

### **Desktop (optional)**

* Visualization
* Simulation environment
* Not needed for contest

---

# **Security Model**

* All packets include HMAC-Lite signatures
* Rotation key per-boot
* Prevents impersonation/spoofing
* No heavy crypto (battery constraints)
* No personal data transmitted
* No identifiers stored long-term

---

# **Demo Plan**

The contest demo will show:

### **Halo Core Demo**

1. Two phones in airplane mode discovering each other
2. Multi-hop routing with a third device
3. Survivor map populating
4. SOS messages propagating
5. Dead-reckoning position tracking indoors

### **Halo AR Demo (only if built)**

* Camera view
* AR markers appearing behind walls / obstacles
* Distance labels tracking as rescuer moves
* Survivor cluster heatmap

---

# **Implementation Roadmap**

### **Phase 1 (Weeks 1–3)**

* KMP project setup
* BLE scan/advertise on both platforms
* Simple peer list
* Packet format + serialization

### **Phase 2 (Weeks 4–6)**

* Routing protocol (basic)
* Node heartbeat
* GPS + dead-reckoning baseline
* Offline map view

### **Phase 3 (Weeks 7–10)**

* Kalman fusion
* Survivor clustering
* SOS messaging
* Ultra-low power mode
* Full MVP polish

### **Phase 4 (Weeks 11–14)** ***(stretch)***

* ARCore/ARKit integration
* Relative positioning overlay
* Triangulation visualization

---

# **Future Extensions**

* Satellite fallback (Iridium SMS-like)
* External UWB sensor support
* Drone-assisted mesh extender node
* Thermal camera integration
* Cloud synchronization for responders

---

# **Conclusion**

**Halo** is a robust, high-impact disaster resilience system demonstrating the full power of **Kotlin Multiplatform** for real-world offline, safety-critical applications.

The MVP is fully attainable.
The optional AR module is ambitious and visually stunning.
Together, they make Halo a **top-tier 2026 KMP Contest contender**.
---
