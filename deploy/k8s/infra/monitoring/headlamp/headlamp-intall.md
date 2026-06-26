### Step 1: Install Headlamp via Helm

Run these commands from your Mac terminal to pull the official chart and deploy it into `kube-system`:

```bash
# 1. Add and update the official repository
helm repo add headlamp https://kinvolk.github.io/headlamp
helm repo update

# 2. Install the release
helm install headlamp headlamp/headlamp --namespace kube-system

```

---

### Step 2: Elevate Permissions & Generate Token

Authorize Headlamp to view cluster metrics, then generate your entry token:

```bash
# 1. Grant cluster-admin rights to the service account
kubectl create clusterrolebinding headlamp-admin-binding \
  --clusterrole=cluster-admin \
  --serviceaccount=kube-system:default

# 2. Generate a 24-hour access token
kubectl create token default -n kube-system --duration=24h

```

*Copy the long token string returned by the terminal.*

---

### Step 3: Expose via URL (Traefik Ingress)

Create a file named `headlamp-ingress.yaml`:

```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: headlamp-url
  namespace: kube-system
  annotations:
    kubernetes.io/ingress.class: traefik
spec:
  rules:
    - host: headlamp.local
      http:
        paths:
          - path: /
            pathType: Prefix
            backend:
              service:
                name: headlamp
                port:
                  number: 80

```

```bash
# Apply the routing blueprint
kubectl apply -f headlamp-ingress.yaml

```

---

### Step 4: Map Local Hosts and Log In

1. Open `/etc/hosts` on your Mac (`sudo nano /etc/hosts`) and add your Pi's IP:

```text
<YOUR_PI_IP_ADDRESS>   headlamp.local

```

2. Navigate to `http://headlamp.local` in your Mac browser and paste your token to log in.