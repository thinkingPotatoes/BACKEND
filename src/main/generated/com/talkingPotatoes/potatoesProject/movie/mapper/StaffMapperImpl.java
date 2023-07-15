package com.talkingPotatoes.potatoesProject.movie.mapper;

import com.talkingPotatoes.potatoesProject.movie.dto.StaffDto;
import com.talkingPotatoes.potatoesProject.movie.entity.Staff;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-08T15:31:33+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class StaffMapperImpl implements StaffMapper {

    @Override
    public Staff toEntity(StaffDto staffDto) {
        if ( staffDto == null ) {
            return null;
        }

        Staff.StaffBuilder staff = Staff.builder();

        staff.id( staffDto.getId() );
        staff.docId( staffDto.getDocId() );
        staff.staffId( staffDto.getStaffId() );
        staff.staffNm( staffDto.getStaffNm() );
        staff.staffRoleGroup( staffDto.getStaffRoleGroup() );
        staff.staffRole( staffDto.getStaffRole() );

        return staff.build();
    }

    @Override
    public StaffDto toDto(Staff staff) {
        if ( staff == null ) {
            return null;
        }

        StaffDto.StaffDtoBuilder staffDto = StaffDto.builder();

        staffDto.id( staff.getId() );
        staffDto.docId( staff.getDocId() );
        staffDto.staffId( staff.getStaffId() );
        staffDto.staffNm( staff.getStaffNm() );
        staffDto.staffRoleGroup( staff.getStaffRoleGroup() );
        staffDto.staffRole( staff.getStaffRole() );

        return staffDto.build();
    }

    @Override
    public List<Staff> toEntity(List<StaffDto> staffDtoList) {
        if ( staffDtoList == null ) {
            return null;
        }

        List<Staff> list = new ArrayList<Staff>( staffDtoList.size() );
        for ( StaffDto staffDto : staffDtoList ) {
            list.add( toEntity( staffDto ) );
        }

        return list;
    }

    @Override
    public List<StaffDto> toDto(List<Staff> staffList) {
        if ( staffList == null ) {
            return null;
        }

        List<StaffDto> list = new ArrayList<StaffDto>( staffList.size() );
        for ( Staff staff : staffList ) {
            list.add( toDto( staff ) );
        }

        return list;
    }
}
