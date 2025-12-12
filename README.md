# Halo  
## Offline Survivor Localization Using Camera-Based Relative Positioning  
*Apple Swift Student Challenge Submission*

---

## Overview

**Halo** is an offline, infrastructure-independent survivor localization system designed for disaster scenarios where cellular networks, GPS, and internet connectivity are unavailable.

Halo turns **phones into local positioning instruments**, using on-device sensors and the camera to estimate **relative positions of nearby devices and people**, helping survivors find each other and helping responders locate clusters of people without relying on centralized infrastructure.

---

## Problem Statement

In natural disasters and large-scale emergencies:

- Cellular towers are damaged or overloaded.
- GPS becomes unreliable indoors, underground, or near collapsed structures.
- Internet access is intermittent or nonexistent.
- People are separated from family, rescuers, and safe exits.
- Existing emergency tools assume either connectivity or direct visibility.

Current solutions fail in the most common disaster condition: **fragmented or zero infrastructure**.

---

## Core Insight

Even when networks fail, **phones can still see, sense, and reason locally**.

Halo leverages:
- the camera,
- motion sensors,
- short-range peer discovery,

to answer a critical question:

> *“Who is near me, and where are they relative to my position?”*

This reframes disaster response from **global location** to **local awareness**.

---

## Who Uses Halo

### Primary Users: Survivors
- Individuals trapped, displaced, or navigating unsafe environments
- Families trying to regroup
- People attempting to find others nearby without communication

Survivors actively use Halo to:
- locate nearby people,
- move toward safety together,
- avoid isolation.

### Secondary Users: First Responders
- Search and rescue teams
- Emergency personnel entering low-visibility or GPS-denied environments

Responders use Halo to:
- identify survivor clusters,
- prioritize search zones,
- navigate toward groups of people.

---

## What Halo Is Not

- Not a GPS replacement
- Not a chat or messaging app
- Not dependent on cloud services
- Not a social network
- Not a live tracking system

Halo focuses strictly on **local, relative positioning**.

---

## How Halo Works (High-Level)

1. **Peer Discovery**
   - Devices discover nearby phones using short-range communication.
   - No internet or cellular network is required.

2. **Camera-Based Sensing**
   - The camera scans the environment.
   - Visual features and motion are used to estimate relative distances and directions.

3. **Dead-Reckoning**
   - Motion sensors track movement over time.
   - Relative positions update even when visual contact is lost.

4. **Local Position Graph**
   - Devices build a local graph of nearby peers.
   - Positions are relative, not absolute.

5. **Guidance Interface**
   - Users see directional cues indicating nearby people.
   - Optional AR overlays help visualize where others are located.

---

## Privacy and Safety by Design

Halo is designed to minimize risk and misuse:

- No global location sharing
- No identity or personal data exchange
- No cloud storage
- All computation happens on-device
- Data exists only locally and temporarily

Relative position data expires automatically.

---

## Why Halo Is Not a Gimmick

Halo aligns with real disaster behavior:

- People first try to find others nearby.
- Local visibility and sound are often limited.
- Infrastructure-dependent systems fail first.

Halo works within these constraints and **lowers the barrier to coordination** during emergencies.

It does not promise precision; it provides **directional awareness**.

---

## Why This Is an Apple-First Project

Halo is deeply aligned with Apple’s platform strengths:

- Camera-first design
- On-device intelligence
- Sensor fusion
- Privacy-preserving computation
- Tight integration with iOS frameworks

This project demonstrates what is possible **only on a modern smartphone**, without external infrastructure.

---

## Minimal Viable Product (MVP)

The MVP focuses on a single, high-impact capability:

- Discover nearby devices offline
- Estimate their relative direction and distance
- Display clear guidance to help users move toward each other

This keeps the system focused, reliable, and demonstrable.

---

## Novelty and Impact

Halo introduces a new disaster-response primitive:

> **Local awareness without global connectivity**

By shifting from absolute location to relative positioning, Halo enables coordination in environments where traditional systems fail.

---

## Conclusion

Halo is a humanitarian, offline-first system that:

- Helps people find each other when infrastructure collapses
- Uses camera and sensors instead of networks
- Preserves privacy by design
- Aligns tightly with Apple’s on-device philosophy

It is a practical, technically grounded approach to making disaster response more human and more resilient.

---
