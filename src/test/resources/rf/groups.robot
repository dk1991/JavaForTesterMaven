*** Settings ***
Library    ru.stqa.pft.addressbook.rf.AddressbookKeyWords
Suite Setup    Init Application Manager
Suite Teardown    Stop Application Manager



*** Test Cases ***
Can create Group With Valid Data
    ${old_count} =    Get Group Count
    Create Group    test name    test header    test footer
    ${new_count} =    Get Group Count
    ${expected_count} =    Evaluate    ${old_count} + 1
    Should Be Equal As Integers    ${new_count}    ${expected_count}