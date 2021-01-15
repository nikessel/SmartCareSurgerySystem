<%-- 
    Document   : employeeDashboard
    Created on : 09-Dec-2020, 20:15:47
    Author     : Niklas Sarup-Lytzen ID: 18036644
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<%@ taglib prefix = "ex" uri = "/WEB-INF/custom.tld"%>

<!DOCTYPE html>
<html>
    <head>
        <style>
            <%@include file="/WEB-INF/css/style.css"%>
        </style>
    </head>

    <div class="grid-container">
        <div class="menu-icon">
            <i class="fas fa-bars header__menu"></i>
        </div>

        <header class="header">
            <div class="header__search">Search...</div>
            <div class="header__avatar">Your face</div>
        </header>

        <aside class="sidenav">
            <div class="sidenav__close-icon">
                <i class="fas fa-times sidenav__brand-close"></i>
            </div>
            <ul class="sidenav__list">
                <li class="sidenav__list-item">Item One</li>
                <li class="sidenav__list-item">Item Two</li>
                <li class="sidenav__list-item">Item Three</li>
                <li class="sidenav__list-item">Item Four</li>
                <li class="sidenav__list-item">Item Five</li>
            </ul>
        </aside>

        <main class="main">
            <div class="wide_card">
                <div class="main-header__heading">
                    <table>
                        <thead>
                            <tr>
                                <th>Date</th>
                                <th>Patient name</th>
                                <th>Week number</th>
                                <th>Day of week</th>
                                <th>Hour of day</th>
                                <th>Minute of day</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${consultations}" var="consultation">
                                <tr>
                                    <td>${ex:formatDate(consultation["consultationTime"], "dd-MM-yyyy")}</td>
                                    <td>${consultation.patient.firstName} ${consultation.patient.surName}</td>
                                    <td>${consultation.consultationTime.weekNumber}</td>
                                    <td>${consultation.consultationTime.day}</td>
                                    <td>${consultation.consultationTime.hours}</td>
                                    <td>${consultation.consultationTime.minutes}</td>
                                </tr>
                            </c:forEach>   
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="main-overview">
                <div class="overviewcard">
                    <div class="overviewcard__icon">Overview</div>
                    <div class="overviewcard__info">Card</div>
                </div>
                <div class="overviewcard">
                    <div class="overviewcard__icon">Overview</div>
                    <div class="overviewcard__info">Card</div>
                </div>
                <div class="overviewcard">
                    <div class="overviewcard__icon">Overview</div>
                    <div class="overviewcard__info">Card</div>
                </div>
                <div class="overviewcard">
                    <div class="overviewcard__icon">Overview</div>
                    <div class="overviewcard__info">Card</div>
                </div>
            </div>

            <div class="main-cards">
                <div class="card">Card</div>
                <div class="card">Card</div>
                <div class="card">Card</div>
            </div>
        </main>

    </div>
</html>


