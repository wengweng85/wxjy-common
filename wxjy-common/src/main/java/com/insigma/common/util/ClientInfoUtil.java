package com.insigma.common.util;

import com.insigma.dto.Device;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

import javax.servlet.http.HttpServletRequest;

public class ClientInfoUtil {

    public static Device getDevice(HttpServletRequest request) {

        UserAgent userAgent = UserAgent.parseUserAgentString(
                request.getHeader("User-Agent"));
        Device device = new Device();

        // 获取客户端操作系统
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();

        if (OperatingSystem.WINDOWS.equals(operatingSystem.getGroup())) {
            if (OperatingSystem.WINDOWS_XP.equals(operatingSystem)) {
                device.setEec117("0101");
            } else if (OperatingSystem.WINDOWS_7.equals(operatingSystem)) {
                device.setEec117("0102");
            } else if (OperatingSystem.WINDOWS_8.equals(operatingSystem)) {
                device.setEec117("0103");
            } else if (OperatingSystem.WINDOWS_10.equals(operatingSystem)) {
                device.setEec117("0104");
            } else {
                device.setEec117("0199");
            }
        } else if (OperatingSystem.MAC_OS.equals(operatingSystem.getGroup())) {
            device.setEec117("0201");
        } else if (OperatingSystem.ANDROID.equals(operatingSystem.getGroup())) {
            if (OperatingSystem.ANDROID4.equals(operatingSystem)) {
                device.setEec117("0301");
            } else if (OperatingSystem.ANDROID5.equals(operatingSystem)) {
                device.setEec117("0302");
            } else if (OperatingSystem.ANDROID6.equals(operatingSystem)) {
                device.setEec117("0303");
            } else if (operatingSystem.getName().contains("7")) {
                device.setEec117("0304");
            } else {
                device.setEec117("0399");
            }
        } else if (OperatingSystem.IOS.equals(operatingSystem.getGroup())) {
            if (OperatingSystem.iOS8_IPHONE.equals(operatingSystem.getGroup())) {
                device.setEec117("0401");
            } else if (OperatingSystem.iOS9_IPHONE.equals(operatingSystem.getGroup())) {
                device.setEec117("0402");
            } else if (operatingSystem.getName().contains("10")) {
                device.setEec117("0403");
            } else if (operatingSystem.getName().contains("11")) {
                device.setEec117("0404");
            } else {
                device.setEec117("0499");
            }
        } else {
            device.setEec117("99");
        }

        // 获取客户端浏览器类型
        Browser browser = userAgent.getBrowser();
        if (Browser.CHROME.equals(browser.getGroup())) {
            device.setEec118("01");
        } else if (Browser.FIREFOX.equals(browser.getGroup())) {
            device.setEec118("02");
        } else if (Browser.IE.equals(browser.getGroup())) {
            device.setEec118("03");
        } else if (Browser.EDGE.equals(browser.getGroup())) {
            device.setEec118("04");
        } else if (Browser.SAFARI.equals(browser.getGroup())) {
            device.setEec118("05");
        } else {
            device.setEec118("99");
        }

        // 获取客户端ip
        device.setEec119(IPUtil.getClientIpAddr(request));
        return device;
    }
}
