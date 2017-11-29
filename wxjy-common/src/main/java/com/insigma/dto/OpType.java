package com.insigma.dto;

import com.alibaba.druid.util.StringUtils;

/**
 * ������־����,���ͱ������
 * ���˴�дP+5λ����
 * ��λ��дC+5λ����
 * ������������¼��־
 */
public enum OpType {

    /************������**************************/
    P00000("P00000", "�Ͷ���-ע������ֻ��˺�"),
    P00001("P00001", "�Ͷ���-��ȡ�ֻ���ע�������֤��"),
    P00002("P00002", "�Ͷ���-��ȡ�ֻ����һ����������֤��"),
    P10000("P10000", "�Ͷ���-ע������˺�"),
    P10001("P10001", "�Ͷ���-��¼�����˺�"),
    P10002("P10002", "�Ͷ���-����������Ƭ"),
    P10003("P10003", "�Ͷ���-�޸ĸ��˻�����Ϣ"),
    P10004("P10004", "�Ͷ���-�ϴ�����ͷ��"),
    P10005("P10005", "�Ͷ���-����������ְ����"),
    P10006("P10006", "�Ͷ���-�޸ļ�����ְ����"),
    P10007("P10007", "�Ͷ���-����������������"),
    P10008("P10008", "�Ͷ���-�޸ļ�����������"),
    P10009("P10009", "�Ͷ���-ɾ��������������"),
    P10010("P10010", "�Ͷ���-����������������"),
    P10011("P10011", "�Ͷ���-�޸ļ�����������"),
    P10012("P10012", "�Ͷ���-ɾ��������������"),
    P10013("P10013", "�Ͷ���-����������������"),
    P10014("P10014", "�Ͷ���-�޸ļ�����������"),
    P10015("P10015", "�Ͷ���-ɾ��������������"),
    P10016("P10016", "�Ͷ���-�޸ļ�����������"),
    P10017("P10017", "�Ͷ���-��������������Ϣ"),
    P10018("P10018", "�Ͷ���-�޸ļ���������Ϣ"),
    P10019("P10019", "�Ͷ���-ˢ�¼���"),
    P10020("P10020", "�Ͷ���-�޸ļ�������״̬"),
    P10021("P10021", "�Ͷ���-���ؼ���"),
    P10022("P10022", "�Ͷ���-���ͼ����ʼ�"),

    /************������**************************/
    P20000("P20000", "�Ͷ���-"),


    /************����**************************/
    C10000("C10000", "���˵�λ-ע�ᵥλ�˺�"),
    C10001("C10001", "���˵�λ-��¼��λ�˺�"),
    C10002("C10002", "���˵�λ-������λ��Ƭ"),
    C10003("C10003","���˵�λ-�޸ĵ�λ������Ϣ"),
    C10004("C10004","���˵�λ-����ְλ��Ϣ"),
    C10005("C10005","���˵�λ-�޸�ְλ��Ϣ"),
    C10006("C10006","���˵�λ-ְλ����"),
    C10007("C10007","���˵�λ-ְλ����"),
    C10008("C10008","���˵�λ-ְλɾ��"),
    C10009("C10009","���˵�λ-ְλɾ���ָ�"),


    /************ë��**************************/
    C20000("C20000","���˵�λ-�鿴����"),
    C20001("C20001","���˵�λ-�鿴�յ��ļ���"),
    C20002("C20002","���˵�λ-���ؼ���"),
    C20003("C20003","���˵�λ-�ղؼ���"),
    C20004("C20004","���˵�λ-ȡ���ղؼ���"),
    C20005("C20005","���˵�λ-������������"),
    C20006("C20006","���˵�λ-�Ǽ����Է���"),
    C20007("C20007","���˵�λ-�޸����Է���"),
    C20008("C20008","���˵�λ-������Ƹ��"),
    C20009("C20009","���˵�λ-����������Ƹ��"),
    C20010("C20010","���˵�λ-�޸�����"),
    C20011("C20011","���˵�λ-�������˺�"),
    C20012("C20012","���˵�λ-�޸����˺�"),
    C20013("C20013","���˵�λ-ɾ�����˺�"),


    /************�켽��**************************/
    P30000("P30000", "�Ͷ���-�������ְλ��¼"),
    P30001("P30001", "�Ͷ���-Ͷ�ݼ���"),
    P30002("P30002", "�Ͷ���-�ղ�ְλ");

    private String code;
    private String name;

    OpType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static OpType getSexEnumByCode(String code){
        for(OpType opType : OpType.values()){
            if(StringUtils.equals(code, opType.getCode())){
                return opType;
            }
        }
        return null;
    }
}