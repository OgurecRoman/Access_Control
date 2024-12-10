#ifndef DEMO_CAMERA_H
#define DEMO_CAMERA_H

#include <string>

class Camera {
public:
    Camera() = default;
    Camera(long id, const std::string& ip, long idOfEnterprise)
        : id(id), ip(ip), idOfEnterprise(idOfEnterprise) {}

    long getId() const { return id; }
    void setId(long id) { this->id = id; }

    const std::string& getIp() const { return ip; }
    void setIp(const std::string& ip) { this->ip = ip; }

    long getIdOfEnterprise() const { return idOfEnterprise; }
    void setIdOfEnterprise(long idOfEnterprise) { this->idOfEnterprise = idOfEnterprise; }

    std::string toString() const {
        return "Camera{" +
            "id=" + std::to_string(id) +
            ", ip='" + ip + '\'' +
            ", idOfEnterprise=" + std::to_string(idOfEnterprise) +
            '}';
    }

private:
    long id;
    std::string ip;
    long idOfEnterprise;
};

#endif // DEMO_CAMERA_H
