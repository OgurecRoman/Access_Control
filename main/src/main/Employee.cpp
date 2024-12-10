#ifndef DEMO_EMPLOYEE_H
#define DEMO_EMPLOYEE_H

#include <string>
#include <chrono>

class Employee {
public:
    Employee() = default;
    Employee(long id, const std::string& name, int age, const std::chrono::system_clock::time_point& dateTime, long idOfEnterprise, const std::string& photo)
        : id(id), name(name), age(age), dateTime(dateTime), idOfEnterprise(idOfEnterprise), photo(photo) {}

    long getId() const { return id; }
    void setId(long id) { this->id = id; }

    const std::string& getName() const { return name; }
    void setName(const std::string& name) { this->name = name; }

    int getAge() const { return age; }
    void setAge(int age) { this->age = age; }

    const std::chrono::system_clock::time_point& getDateTime() const { return dateTime; }
    void setDateTime(const std::chrono::system_clock::time_point& dateTime) { this->dateTime = dateTime; }

    long getIdOfEnterprise() const { return idOfEnterprise; }
    void setIdOfEnterprise(long idOfEnterprise) { this->idOfEnterprise = idOfEnterprise; }

    const std::string& getPhoto() const { return photo; }
    void setPhoto(const std::string& photo) { this->photo = photo; }

    std::string toString() const {
        return "Employee{" +
            "id=" + std::to_string(id) +
            ", name='" + name + '\'' +
            ", age=" + std::to_string(age) +
            ", dateTime=" + std::to_string(dateTime.time_since_epoch().count()) +
            ", idOfEnterprise=" + std::to_string(idOfEnterprise) +
            '}';
    }

private:
    long id;
    std::string name;
    int age;
    std::chrono::system_clock::time_point dateTime;
    long idOfEnterprise;
    std::string photo;
};

#endif // DEMO_EMPLOYEE_H